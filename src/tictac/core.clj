(ns tictac.core
  (:require [criterium.core :as c]))

(def winning-boards [
    [1 1 1
     0 0 0
     0 0 0],

    [0 0 0
     1 1 1
     0 0 0],

    [0 0 0
     0 0 0
     1 1 1],

    [1 0 0
     1 0 0
     1 0 0],

    [0 1 0
     0 1 0
     0 1 0],

    [0 0 1
     0 0 1
     0 0 1],

    [1 0 0
     0 1 0
     0 0 1],

    [0 0 1
     0 1 0
     1 0 0]
])
(defn horizontal-victory? [arrboard]
  (or ()
   ))
(def board [[:x :o nil]
            [:x nil nil]
            [:x :o nil]])
(defn compile-bitboard [board sign]
  (map #(if (= sign %) 1 0) (flatten board)))

(defn compile-arrboard [[row1 row2 row3] sign]
  [(vec (map #(if (= sign %) 1 0) row1))
   (vec (map #(if (= sign %) 1 0) row2))
   (vec (map #(if (= sign %) 1 0) row3))])

(defn generate-random-arrboard []
   (vec (map #(vec %) (partition 3 (take 9 (repeatedly #(rand-int 2)))))))
(def randarrboard (generate-random-arrboard))
randarrboard
(compile-bitboard board :x)
(compile-arrboard board :x)
;=>3.815241
(c/bench (.contains (map #(apply + %) randarrboard) 3))
(.contains (map #(apply + %) randarrboard) 3)

(defn victory? [bitboard, [first-winning-board & rest-winning-boards]]
  (or
     (= (map bit-and bitboard first-winning-board) first-winning-board)
     (and (not (empty? rest-winning-boards))
          (victory? bitboard rest-winning-boards))))


(defn generate-random-bitboard []
   (vec (take 9 (repeatedly #(rand-int 2)))))

(c/bench (victory? (generate-random-bitboard) winning-boards))
  ;=> 7.462551 µs
