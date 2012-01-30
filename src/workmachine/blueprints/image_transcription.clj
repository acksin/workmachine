(ns workmachine.blueprints.image-transcription
  (:use workmachine.workflow))

(def program
  '((:ocr
     {:execute [[:ocr "image_url"]]
      :output [[:string "image_text"]]})
    (:edit
     {:input [[:text "image_text"]]
      :output [[:text "edited_text"]]})))

(defn workflow [workflow-jobs]
  (doseq [job workflow-jobs]
    (start-workflow program job)))

