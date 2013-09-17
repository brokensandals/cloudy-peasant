(ns misc.output
  (:import [java.io File])
  (:use clojure.java.browse
        clojure.java.shell))

(defn tempfile [text suffix]
  (let [file (File/createTempFile "misc-" suffix)]
    (.deleteOnExit file)
    (spit file text)
    file))

(defn subl [file]
  (sh "subl" (str file)))

(defn diff-text [str1 str2]
  (:out
    (sh "diff" (str (tempfile str1 nil)) (str (tempfile str2 nil)))))

(defn browse-html-string [body]
  (browse-url (str "file://" (tempfile body ".html"))))
