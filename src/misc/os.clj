(ns misc.os
  (:import [java.awt Toolkit]
           [java.awt.datatransfer DataFlavor]))

(defn get-clipboard-string []
  (-> (Toolkit/getDefaultToolkit) .getSystemClipboard (.getData DataFlavor/stringFlavor)))
