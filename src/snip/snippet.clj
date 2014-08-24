(ns snip.snippet
  (:require [snip.tokenize :as t])
  (:import clojure.lang.Cons))

(defn snippet
  "Extracts the snippet from the front of the given sequence of lines."
  ([ls] (snippet ls 1))

  ([ls lvl]
   (if (= lvl 0)
     '()
     (lazy-seq
       (when-let [[l & ls'] ls]
         (cond
           (= l ::t/END) (snippet ls' (dec lvl))
           (keyword? l) (snippet ls' (inc lvl))
           :else (cons l (snippet ls' lvl))))))))

(defn snip-seq
  "Converts a sequence of tokenized lines into a sequence of snippets."
  [ls]
  (lazy-seq
    (when-let [[l & ls'] (seq ls)]
      (if (and (keyword? l) (not= ::t/END l))
        (cons [l (snippet ls')] (snip-seq ls'))
        (snip-seq ls')))))
