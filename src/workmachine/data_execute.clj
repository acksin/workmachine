(ns workmachine.data-execute
  (:use clojure.contrib.shell-out))

(defn image-ocr [image-field text-field]
  (fn [value]
    (sh "convert" (str value "-type Grayscale " value ".tif"))
    (sh "tesseract" (str value ".tif") (str "/tmp/" value ".ocr"))
    (sh "cat" (str "/tmp/" value ".ocr.txt")))})

(defn image-ocr-train [image-field training-text-field]
  (fn [value]
    ;; http://code.google.com/p/tesseract-ocr/wiki/TrainingTesseract3
    )})

(defn audio-transcribe [audio-field text-field]
  ;; TODO
  )

(defn audio-transcribe-train [audio-field training-text-field]
  ;; TODO
  )
  
(defn parse [field]
  (apply (case (first field)
           :audio-transcribe audio-transcribe
           :audio-transcribe-train audio-transcribe-train
           :image-ocr image-ocr
           :image-ocr-train image-ocr-train
         (rest field))))