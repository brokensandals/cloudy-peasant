(ns misc.fnserve
  (:use compojure.response
        hiccup.core
        hiccup.form
        hiccup.page
        misc.format
        [ring.middleware keyword-params params stacktrace]
        ring.util.response)
  (:require [compojure.route :as route]))

; Display

(defn render-json-object [obj]
  (html [:pre (-> obj format-json h)]))

; Response Generation

(defn json-request? [req]
  (= "json" (-> req :params :format))) ; todo: use accept header

(defn respond-with-json-object [req obj]
  (if (json-request? req)
    (-> obj format-json response (content-type "application/json"))
    (-> obj render-json-object (render req))))

(defn respond-with-required-params [req args]
  (if (json-request? req)
    (-> (str "Required arguments: " args) response (status 400))
    (render
      (html5
        (form-to [:get ""] ; todo: don't really want everything to be a get, obviously
          (for [arg args]
            [:div
              (label arg arg)
              (text-field arg ((keyword arg) (:params req)))])
          (submit-button "Submit")))
      req)))

; Handlers

(defn has-all-params? [req required]
  (every? (-> req :params keys set) (map keyword required)))

(defmacro simple-fn-handler [[& args] & body]
  `(-> (fn [req#]
         (if (has-all-params? req# '~args)
           (let [~(into {} (for [arg args] [arg (keyword arg)])) (:params req#)]
             (respond-with-json-object req# (do ~@body)))
           (respond-with-required-params req# '~args)))
       wrap-keyword-params
       wrap-params
       wrap-stacktrace-web))
