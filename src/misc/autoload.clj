(ns misc.autoload
  (:use clojure.tools.namespace.file
        watchtower.core))

(defn auto-use [target-ns dirs]
  (watcher dirs
    (rate 500)
    (file-filter ignore-dotfiles)
    (file-filter (extensions :clj))
    (on-change
      (fn [files]
        (doseq [ns-decl (map read-file-ns-decl files)]
          (when ns-decl
            (try
              (binding [*ns* target-ns]
                (use (second ns-decl) :reload)
              (catch Throwable e (.printStackTrace e))))))))))
