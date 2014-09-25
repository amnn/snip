(ns snip.file-utils
  (:require [clojure.java.io :as io]
            [clojure.string :as string])
  (:import java.util.regex.Pattern
           java.io.File))

(defn canonicalize-path [path]
  "Returns the canoncial path for the given path"
  (.getCanonicalFile (io/as-file path)))

(defn- ext-split [file]
  "Split name of file on periods"
  (string/split (.getName file) #"\."))


(defn extension [file]
  "Returns the extension for a particular file"
  (-> (ext-split file)
      rest
      (->> (string/join "."))))

(defn strip-ext [file]
  "File name with extension stripped"
  (first (ext-split file)))

(defn- dir [file]
  "Returns the directory of the file"
  (.getParentFile file))

(defn files [files-in recursive? hidden?]
  "Takes a sequence of files `files-in` and returns the sequence of files that
  should be processed according to whether the files should be traversed
  `recursive?`-ly and whether `hidden?` files should be included."
  (->>
    (if recursive?
      (mapcat #(-> % canonicalize-path file-seq) files-in)
      (map canonicalize-path files-in))
    (filter (fn [file]
              (and (.isFile file)
                   (or hidden?
                       (not (.isHidden file))))))))

(defn- split-path [path]
  "Splits a path into its constituent components along its separators"
  (string/split path (re-pattern (Pattern/quote File/separator))))

(defn- join-path [parts]
  "Joins the constituent parts of a path using the File separator"
  (string/join File/separator parts))

(defn- shared-path [out src]
  "Returns the parts of the path that are shared between the two given paths."
  (let [abs-out (split-path (.getAbsolutePath out))
        abs-src (split-path (.getAbsolutePath src))]
    (->>
      (map #(when (= % %2) %)
           abs-out abs-src)
      (take-while some?))))

(defn- out-relative-path [out src]
  "Returns the parts of the path relative to the path shared by the output
  path."
  (->> src
       .getAbsolutePath
       split-path
       (drop (count (shared-path out src)))))

(defn snippet-file [out src snip]
  "Generates the snippet file from the output directory, source file name, and
  snippet name."
  (-> (out-relative-path out (dir src))
      vec
      (conj (strip-ext src)
            (str (name snip) \. (extension src)))
      (->> (string/join "_")
           (io/file out))))
