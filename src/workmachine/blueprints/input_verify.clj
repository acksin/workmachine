(ns workengine.blueprints.input-verify
  (:use workengine.workflow))


;; Create a form structure here.
;; :html          => html representation of the the inputs and outputs
;; :input-fields  => input-fields
;; :output-fields => output-fields
;; :job-input     => the inputs for the input-fields
;; :job-output    => the inputs from the workers.
(define-instruction data-input [input-fields output-fields]
  {:html (input-types/data-input-html-form input-fields output-fields job-input)
   :input-fields input-fields
   :output-fields output-fields
   :job-input job-input
   :job-output nil})

(define-instruction data-verify []
  {})

(define-workflow workflow
  '((:input
     (data-input
      [(input-types/image "image_url")
       (input-types/audio "audio_url")]
      [(output-types/string "phone")]))
    (:verify
     (data-verify-job))))
