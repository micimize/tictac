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
(def board [[:x :o nil]
            [:x nil nil]
            [:x :o nil]])
(defn compile-bitboard [board sign]
  (map #(if (= sign %) 1 0) (flatten board)))

(compile-bitboard board :x)

(defn victory? [bitboard, [first-winning-board & rest-winning-boards]]
  (or
     (= (map bit-and bitboard first-winning-board) first-winning-board)
     (and (not (empty? rest-winning-boards))
          (victory? bitboard rest-winning-boards))))


(defn generate-random-bitboard []
   (vec (take 9 (repeatedly #(rand-int 2)))))

(c/bench (victory? (generate-random-bitboard) winning-boards))
  ;=> 7.462551 µs
