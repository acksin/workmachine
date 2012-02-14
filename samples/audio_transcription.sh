;; NOT Functional Yet. This is largely a template for what is to come.

(ns workmachine.blueprints.audio-transcription
  (:use workmachine.workflow))

(def program
  '((:transcribe
     {:execute :audio-transcribe "audio_url" "audio_text"})
    (:edit
     {:input [[:text "audio_text"]]
      :output [[:text "edited_text"]]})
    (:train
     {:execute :audio-transcribe-train "audio_url" "edited_text"})))

(defn workflow [workflow-jobs]
  (doseq [job workflow-jobs]
    (start-workflow program job)))
