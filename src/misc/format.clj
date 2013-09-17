(ns misc.format
  (:import [java.io StringWriter])
  (:require [clojure.data.json :as json]))

(defn format-json [obj]
  (binding [*out* (StringWriter.)]
    (json/pprint obj :escape-slash false)
    (str *out*)))

(defn reformat-json [text]
  (format-json (json/read-str text)))
