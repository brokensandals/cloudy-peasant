(ns misc.server
  (:use hiccup.middleware
        ring.adapter.jetty))

(defonce handlers (atom {}))

(def server-port 8100)
(def server-host "localhost")

(defn mount-handler!
  ([handler] (mount-handler! (str (System/currentTimeMillis)) handler))
  ([context-root handler]
    (swap! handlers assoc context-root
      (wrap-base-url handler (str "/" context-root)))
    (str "http://" server-host ":" server-port "/" context-root)))

(defn run-server! []
  (run-jetty #((get @handlers (-> % :uri (.split "/") second) (constantly nil)) %)
    {:port server-port
     :max-threads 3}))
