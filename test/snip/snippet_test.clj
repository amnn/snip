(ns snip.snippet-test
  (:require [clojure.test :refer :all]
            [snip.snippet :refer :all]
            [snip.tokenize :as t]))

(deftest snippet-test
  (testing "no annotations"
    (let [ls ["a" "b" "c" "d"]]
      (is (= ls (snippet ls)))))

  (testing "annotations"
    (is (= ["a" "b" "c"]
           (snippet ["a" "b" "c" ::t/END "d" "e" "f"]))))

  (testing "nested annotations"
    (is (= ["a" "b" "c"]
           (snippet ["a" :a "b" ::t/END "c" ::t/END "d"])))))

(deftest snip-seq-test
  (testing "no annotations"
    (is (= [] (snip-seq ["a" "b" "c" "d"]))))

  (testing "annotations"
    (is (= [[:a ["a" "b"]] [:d ["d" "e"]]]
           (snip-seq ["z" :a "a" "b" ::t/END "c" :d "d" "e" ::t/END "f"]))))

  (testing "nested annotations"
    (is (= [[:a ["a" "b" "c" "d"]]
            [:b ["b" "c"]]]
           (snip-seq [:a "a" :b "b" "c" ::t/END "d" ::t/END "e"])))))
