package kchat.core

interface Command<P, T> {
    fun execute(params: P): T
}