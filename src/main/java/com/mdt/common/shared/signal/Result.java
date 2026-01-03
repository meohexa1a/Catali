package com.mdt.common.shared.signal;

import jakarta.annotation.Nonnull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface Result<T, F extends Failure> {

    default boolean isSuccess() {
        return true;
    }

    // !----------------------------------------------!

    static <T, F extends Failure> Result<T, F> success(@Nonnull T value) {
        return new Success<>(value);
    }

    static <F extends Failure> Result<Unit, F> ok() {
        return new Success<>(Unit.INSTANCE);
    }

    static <T, F extends Failure> Result<T, F> empty() {
        return new Empty<>();
    }

    static <T, F extends Failure> Result<T, F> error(@Nonnull F failure) {
        return new Error<>(failure);
    }

    // !-----------------------------------------------!

    default <U> Result<U, F> map(Function<T, U> fn) {
        return switch (this) {
            case Result.Success<T, F> d -> new Success<>(fn.apply(d.value()));
            case Empty<T, F> ignore -> new Empty<>();
            case Error<T, F> e -> new Error<>(e.failure());
        };
    }

    default Result<T, F> provide(Supplier<T> supplier) {
        return switch (this) {
            case Success<T, F> s -> s;
            case Empty<T, F> ignore -> new Success<>(supplier.get());
            case Error<T, F> e -> e;
        };
    }

    default Result<T, F> recover(Function<F, T> fn) {
        return switch (this) {
            case Success<T, F> s -> s;
            case Empty<T, F> e -> e;
            case Error<T, F> err -> new Success<>(fn.apply(err.failure()));
        };
    }

    default Result<T, F> failIfEmpty(Supplier<F> failureSupplier) {
        return switch (this) {
            case Success<T, F> s -> s;
            case Empty<T, F> ignore -> new Error<>(failureSupplier.get());
            case Error<T, F> e -> e;
        };
    }

    default <G extends Failure> Result<T, G> mapError(Function<F, G> fn) {
        return switch (this) {
            case Success<T, F> s -> new Success<>(s.value());
            case Empty<T, F> ignore -> new Empty<>();
            case Error<T, F> e -> new Error<>(fn.apply(e.failure()));
        };
    }


    default <U> Result<U, F> flatMap(Function<T, Result<U, F>> fn) {
        return switch (this) {
            case Success<T, F> s -> fn.apply(s.value());
            case Empty<T, F> ignore -> new Empty<>();
            case Error<T, F> e -> new Error<>(e.failure());
        };
    }

    default Result<T, F> provideWith(Supplier<Result<T, F>> supplier) {
        return switch (this) {
            case Success<T, F> s -> s;
            case Empty<T, F> ignore -> supplier.get();
            case Error<T, F> e -> e;
        };
    }

    default Result<T, F> recoverWith(Function<F, Result<T, F>> fn) {
        return switch (this) {
            case Success<T, F> s -> s;
            case Empty<T, F> e -> e;
            case Error<T, F> err -> fn.apply(err.failure());
        };
    }

    default <G extends Failure> Result<T, G> flatMapError(Function<F, Result<T, G>> fn) {
        return switch (this) {
            case Success<T, F> s -> new Success<>(s.value());
            case Empty<T, F> ignore -> new Empty<>();
            case Error<T, F> e -> fn.apply(e.failure());
        };
    }

    // !-----------------------------------------------!

    default SuccessStatus<T, F> ensureSuccessStatus(Function<F, T> fn) {
        return switch (this) {
            case Success<T, F> s -> s;
            case Empty<T, F> e -> e;
            case Error<T, F> err -> new Success<>(fn.apply(err.failure()));
        };
    }

    default <U> Success<U, F> foldToSuccess(Function<T, U> onSuccess, Supplier<U> onEmpty, Function<F, U> onError) {
        return switch (this) {
            case Success<T, F> s -> new Success<>(onSuccess.apply(s.value()));
            case Empty<T, F> ignored -> new Success<>(onEmpty.get());
            case Error<T, F> e -> new Success<>(onError.apply(e.failure()));
        };
    }

    // !-----------------------------------------------!

    default <R> R fold(Function<T, R> onSuccess, Supplier<R> onEmpty, Function<F, R> onError) {
        return switch (this) {
            case Success<T, F> s -> onSuccess.apply(s.value());
            case Empty<T, F> ignored -> onEmpty.get();
            case Error<T, F> e -> onError.apply(e.failure());
        };
    }

    default void handle(Consumer<T> onSuccess, Runnable onEmpty, Consumer<F> onError) {
        switch (this) {
            case Success<T, F> s -> onSuccess.accept(s.value());
            case Empty<T, F> ignored -> onEmpty.run();
            case Error<T, F> e -> onError.accept(e.failure());
        }
    }

    // !-----------------------------------------------!

    default Result<T, F> onSuccess(Consumer<T> c) {
        if (this instanceof Success<T, F>(T value)) c.accept(value);
        return this;
    }

    default Result<T, F> onEmpty(Runnable r) {
        if (this instanceof Empty<T, F>) r.run();
        return this;
    }

    default Result<T, F> onError(Consumer<F> c) {
        if (this instanceof Error<T, F>(F failure)) c.accept(failure);
        return this;
    }

    // !-----------------------------------------------!

    sealed interface SuccessStatus<T, F extends Failure> permits Success, Empty {
        // TODO: Map to Success & Final if needed
    }

    record Success<T, F extends Failure>(@Nonnull T value) implements Result<T, F>, SuccessStatus<T, F> {

    }

    record Empty<T, F extends Failure>() implements Result<T, F>, SuccessStatus<T, F> {

    }

    record Error<T, F extends Failure>(@Nonnull F failure) implements Result<T, F> {

        @Override
        public boolean isSuccess() {
            return false;
        }
    }
}
