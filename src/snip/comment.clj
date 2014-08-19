(ns snip.comment)

(def
  ^{:doc "Map from languages to comment delimiters."}
  lang->cmnt
  {"clj"  ";;"
   "hs"   "--"
   "rb"   "#"
   "py"   "#"
   "lua"  "--"
   "js"   "//"
   "c"    "//"
   "h"    "//"
   "hpp"  "//"
   "cpp"  "//"
   "m"    "//"
   "mpp"  "//"
   "java" "//"})
