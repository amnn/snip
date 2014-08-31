(ns snip.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.java.io :refer [reader writer]]
            [snip.snippet :as snip]
            [snip.tokenize :as tok]
            [snip.comment :refer :all]
            [snip.file-utils :refer :all])
  (:gen-class))

(def ^:private cli-opts
  [["-o" "--output DIRECTORY" "Defaults to current working directory"
    :default "."
    :parse-fn canonicalize-path]
   ["-c" "--comment COMMENT SYNTAX" "The comment prefix"
    :default nil]
   ["-r" "--recursive" "Treat each file/directory recursively"]
   ["-H" "--include-hidden" "Include hidden files"]
   ["-h" "--help" "Print this help summary"]])

(defn- parse-file [file cmnt]
  "Convert a file into a snip-seq"
  (->>
    file
    line-seq
    (tok/tokenize
      (tok/tokenizer cmnt))
    snip/snip-seq))

(defn- process-file [f out]
  "Extract the snippets from a given file path `f`, and puts them in `out`."
  (with-open [file (reader f)]
      (doseq [[label snippet] (parse-file file ";;")
              :let [s-name (snippet-file out f label)]]
        (with-open [wrt (writer s-name :append false)]
          (doseq [line snippet]
            (doto wrt
              (.append line)
              (.append \newline)))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [{files-in :arguments
         opts     :options
         help     :summary
         err      :errors} (parse-opts args cli-opts)]
    (doseq [e err] (println e))
    (cond
      (or (:help opts) (seq err))
      (println help)

      :else
      (doseq [file (files files-in
                          (:recursive opts)
                          (:include-hidden opts))]
        (process-file file (:output opts))))))
