(ns snip.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [snip.file-utils :refer :all])
  (:gen-class))

(def ^:private cli-opts
  [["-o" "--output" "Output directory, defaults to the current directory"
    :default "."
    :parse-fn canonicalize-path]
   ["-r" "--recursive" "Treat each file/directory recursively"]
   ["-H" "--include-hidden" "Include hidden files"]
   ["-h" "--help" "Print this help summary"]])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [{files :arguments
         opts  :options
         help  :summary
         err   :errors} (parse-opts args cli-opts)]
    (doseq [e err] (println e))
    (cond
      (or (:help opts) (seq err))
      (println help))))
