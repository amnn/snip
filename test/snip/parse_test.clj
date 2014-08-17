(ns snip.parse-test
  (:require [clojure.test :refer :all]
            [snip.parse :refer :all]))

(deftest parser-test
  (testing "matches starting annotations"
    (is (= :annotation
           ((parser ";;") ";; {{{ annotation"))))

  (testing "matches ending annotation"
    (is (= :snip.parse/END
           ((parser "#") "# }}}"))))

  (testing "comment delimiter must match"
    (is (= nil
           ((parser ";;") "// {{{ annotation"))))

  (testing "must be a comment"
    (is (= nil
           ((parser ";;") "{{{ annotation"))))

  (testing "must be an annotation"
    (is (= nil
           ((parser ";;") "annotation")))))

(deftest parse-lines-test
  (testing "parses lines"
    (is (= ["str1"
            :annotation
            "str2"
            :snip.parse/END
            "str3"]
           (parse-lines
             (parser ";;")
             ["str1"
              ";; {{{ annotation"
              "str2"
              ";; }}}"
              "str3"])))))
