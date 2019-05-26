package br.com.luminaspargere.maze2d.domain

inline fun loop(block: () -> Unit): Nothing {
    while (true) block()
}