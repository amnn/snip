(ns snip.tokenize
  (:import  java.util.regex.Pattern))

(defn- re-cmnt [cmnt re]
  (->> re
       (str "\\A" (Pattern/quote cmnt) "\\s*")
       re-pattern))

(defn tokenizer
  "Returns a parser, that, when given a line returns the keyword representing
  the annotation or nil."
  [cmnt]
  (let [re-open (re-cmnt cmnt "\\Q{{{\\E\\s*([\\w-]+)")
        re-close (re-cmnt cmnt "\\Q}}}\\E")]
    #(condp re-matches %
       re-open :>> (fn [[_ ann]] (keyword ann))
       re-close ::END
       nil)))

(defn tokenize
  "Take a sequence of lines and replace the parseable lines with the results
  of applying `parse-fn` on them."
  [token-fn lines]
  (map #(if-let [res (token-fn %)] res %)
       lines))
