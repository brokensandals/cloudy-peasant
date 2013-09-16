(ns misc.output
  (:import [java.io File StringWriter])
  (:require [clojure.data.json :as json])
  (:use clojure.java.shell))

(defn tempfile [text suffix]
  (let [file (File/createTempFile "misc-" suffix)]
    (.deleteOnExit file)
    (spit file text)
    file))

(defn subl [file]
  (sh "subl" (str file)))

(defn format-json [obj]
  (binding [*out* (StringWriter.)]
    (json/pprint obj :escape-slash false)
    (str *out*)))

(defn reformat-json [text]
  (format-json (json/read-str text)))

(defn diff-text [str1 str2]
  (:out
    (sh "diff" (str (tempfile str1 nil)) (str (tempfile str2 nil)))))
