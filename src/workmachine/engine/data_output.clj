(ns workmachine.engine.data-output)

(defmacro define-output
  [input-name html]
  `(defn ~input-name [fname#]
     (let [~'name fname#]
       {:name fname#
        :html ~html})))

(define-output string
  [:input {:type "text"
           :value ""
           :name name}])

(define-output text
  [:textarea {:name name} ""])

(define-output text-list
  [:textarea {:name name}])

(defn parse [field]
  (apply (case (first field)
           :string string
           :text text
           :text-list text-list)
         (rest field)))