(ns tictac.core
  (:require [clojure.string :as s]))

(def winning-bitboards [
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

(defn compile-bitboard [board sign]
  " takes a vector board with vector rows of symbols/strings/ints, or a sring board, and returns a bitboard for the given sign"
  (if (string? board)
    (map #(if (= (first (vec sign)) %) 1 0) (s/replace board #"\s" ""))
    (map #(if (= sign %) 1 0) (flatten board))))

(defn bitboard-statematch? [bitboard, [first-state & rest-states]]
  "returns true if the given bitboard contains one of the states in the second argument"
  (or
     (= (map bit-and bitboard first-state) first-state)
     (and (not (empty? rest-states))
          (bitboard-statematch? bitboard rest-states))))

(defn bitvictory? [bitboard]
  (bitboard-statematch? bitboard winning-bitboards))

(defn victory? [board sign]
  (bitvictory? (compile-bitboard board sign)))


(victory? [[:x  :o  nil]
           [:x  nil :o ]
           [:x  :o  nil]] :x)

(victory? "xon
           xon
           xno" "x")

;; These will also work:
;; (victory? [[1  0  nil] [1  nil 0 ] [1  0  nil]] 1)
;; (victory? [["x" "o" nil] ["x" nil "o"] ["x" "o" nil]] "o")
