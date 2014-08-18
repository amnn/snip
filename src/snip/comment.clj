(ns snip.comment)

(def
  ^{:doc "Map from languages to comment delimiters."}
  lang->cmnt
  {"clj"  ";;"
   "hs"   "--"
   "rb"   "#"
   "py"   "#"
   "js"   "//"
   "c"    "//"
   "h"    "//"
   "hpp"  "//"
   "cpp"  "//"
   "m"    "//"
   "java" "//"})
