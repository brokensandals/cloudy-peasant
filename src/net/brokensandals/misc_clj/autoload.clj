(ns net.brokensandals.misc-clj.autoload
  (:import [java.util.concurrent Executors TimeUnit])
  (:use clojure.tools.namespace.find
        ns-tracker.core))

(def ^:private executor (Executors/newSingleThreadScheduledExecutor))

(defn- reload [tracker]
  (doseq [ns-sym (tracker)]
    (try
      (require ns-sym :reload)
      (catch Throwable e (do
                           (println "Could not reload " ns-sym)
                           (.printStackTrace e))))))

(defn autoreload [dirs]
  (let [tracker (ns-tracker dirs)]
    (.scheduleWithFixedDelay executor #(reload tracker) 100 100 TimeUnit/MILLISECONDS)))

(defn use-all [target-ns dirs]
  (doseq [ns-sym (flatten (map find-namespaces-in-dir dirs))]
    (try
      (binding [*ns* target-ns]
        (use ns-sym))
      (catch Throwable e (do
                           (println "Could not use " ns-sym)
                           (.printStackTrace e))))))

(defn autouse [target-ns dirs]
  (.scheduleWithFixedDelay executor #(use-all target-ns dirs) 0 1 TimeUnit/SECONDS))

(defn autoload-for-repl [dirs]
  (autouse (find-ns 'user) dirs)
  (autoreload dirs))
