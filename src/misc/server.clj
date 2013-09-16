(ns misc.server
  (:use ring.adapter.jetty))

(defonce handlers (atom {}))
(defn add-handler! [context-root handler]
  (swap! handlers assoc context-root handler))

(def server-port 8100)

(defn run-server! []
  (run-jetty #((get @handlers (-> % :uri (.split "/") second) (constantly nil)))
    {:port server-port
     :max-threads 3}))
