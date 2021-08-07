package com.jess.arms.http.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author IurKwan
 * @date 12/8/20
 */
public final class HandlerErrorGsonConverterFactory extends Converter.Factory {

    // 如果我们需要对结果进行自定义的转换的话
    // 作统一的处理，只需要继承 Convert.Factory 然后实现相关的方法，
    // 在 responseBodyConvert中进行统一的处理即可，也就是说，我们
    // 根本就不需要GsonConvert这个库，我们完全可以自定义

    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static HandlerErrorGsonConverterFactory create() {
        return create(new Gson());
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
    public static HandlerErrorGsonConverterFactory create(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        return new HandlerErrorGsonConverterFactory(gson);
    }

    private final Gson gson;

    private HandlerErrorGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(@NotNull Type type, Annotation @NotNull [] annotations,
                                                            @NotNull Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new HandlerErrorGsonResponseBodyConverter<>(adapter);
    }

    @Override
    public Converter<?, String> stringConverter(@NotNull Type type, Annotation @NotNull [] annotations, @NotNull Retrofit retrofit) {
        // 在这里对集合进行统一的处理
        // define customer class to converter to list[0]
        return super.stringConverter(type, annotations, retrofit);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(@NotNull Type type,
                                                          Annotation @NotNull [] parameterAnnotations, Annotation @NotNull [] methodAnnotations, @NotNull Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new HandlerErrorGsonRequestBodyConverter<>(gson, adapter);
    }

}
