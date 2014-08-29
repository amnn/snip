(ns snip.core
  (:require [clojure.java.io :as io])
  (:use [clojure.tools.cli :only [parse-opts]])
  (:gen-class))

(defn- normalize-path [path]
  (.getCanonicalPath (io/as-file path)))

(def ^:private cli-opts
  [["-o" "--output" "Output directory, defaults to the current directory"
    :default "."
    :parse-fn normalize-path]
   ["-r" "--recursive" "Treat each file/directory recursively"]
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
