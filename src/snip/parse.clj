(ns snip.parse
  (:import  java.util.regex.Pattern))

(defn- re-cmnt [cmnt re]
  (->> re
       (str "\\A" (Pattern/quote cmnt) "\\s*")
       re-pattern))

(defn parser
  "Returns a parser, that, when given a line returns the keyword representing
  the annotation or nil."
  [cmnt]
  (let [re-open (re-cmnt cmnt "\\Q{{{\\E\\s*([\\w-]+)")
        re-close (re-cmnt cmnt "\\Q}}}\\E")]
    #(condp re-matches %
       re-open :>> (fn [[_ ann]] (keyword ann))
       re-close ::END
       nil)))

(defn parse-lines
  "Take a sequence of lines and replace the parseable lines with the results
  of applying `parse-fn` on them."
  [parse-fn lines]
  (map #(if-let [res (parse-fn %)] res %)
       lines))
