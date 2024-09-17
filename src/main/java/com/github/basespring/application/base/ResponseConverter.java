package com.github.basespring.application.base;

public abstract class ResponseConverter<Src, Desc> {
    public ResponseConverter() {
    }

    protected abstract Desc convert(Src src);
}