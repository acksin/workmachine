(ns workmachine.data-execute
  (:use clojure.contrib.shell-out))

(defn ocr [name]
  {:name name
   :execute (fn [value]
              (sh "convert" (str value "-type Grayscale " value ".tif"))
              (sh "tesseract" (str value ".tif") (str "/tmp/" value ".ocr"))
              (sh "cat" (str "/tmp/" value ".ocr.txt")))})

(defn ocr-train [name]
  {:name name
   :execute (fn [value]
              ;; http://code.google.com/p/tesseract-ocr/wiki/TrainingTesseract3
              )})

(defn parse [field]
  (apply (case (first field)
           :execute string
         (rest field))))