(ns snip.tokenize-test
  (:require [clojure.test :refer :all]
            [snip.tokenize :refer :all]))

(deftest tokenizer-test
  (testing "matches starting annotations"
    (is (= :annotation
           ((tokenizer ";;") ";; {{{ annotation"))))

  (testing "matches ending annotation"
    (is (= :snip.tokenize/END
           ((tokenizer "#") "# }}}"))))

  (testing "comment delimiter must match"
    (is (= nil
           ((tokenizer ";;") "// {{{ annotation"))))

  (testing "must be a comment"
    (is (= nil
           ((tokenizer ";;") "{{{ annotation"))))

  (testing "must be an annotation"
    (is (= nil
           ((tokenizer ";;") "annotation")))))

(deftest tokenize-lines-test
  (testing "tokenize lines"
    (is (= ["str1"
            :annotation
            "str2"
            :snip.tokenize/END
            "str3"]
           (tokenize
             (tokenizer ";;")
             ["str1"
              ";; {{{ annotation"
              "str2"
              ";; }}}"
              "str3"])))))
