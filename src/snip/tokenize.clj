(ns snip.tokenize
  (:import java.util.regex.Pattern))

(defn- re-cmnt [cmnt re]
  (->> re
       (str "\\A" (Pattern/quote cmnt) "\\s*")
       re-pattern))

(defn tokenizer
  "Creates a tokenizer that extracts annotations from a sequence of lines."
  [cmnt]
  (let [re-open (re-cmnt cmnt "\\Q{{{\\E\\s*([\\w-]+)")
        re-close (re-cmnt cmnt "\\Q}}}\\E")]
    #(condp re-matches %
       re-open :>> (fn [[_ ann]] (keyword ann))
       re-close ::END
       %)))

(defn tokenize
  "Apply a tokenizer over a sequence of lines."
  [token-fn lines]
  (map token-fn lines))
