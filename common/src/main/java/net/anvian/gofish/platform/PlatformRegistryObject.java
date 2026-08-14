package net.anvian.gofish.platform;

import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public final class PlatformRegistryObject<T> implements Supplier<T> {

    private final ResourceLocation id;
    private final AtomicReference<T> value = new AtomicReference<>();

    public PlatformRegistryObject(ResourceLocation id) {
        this.id = id;
    }

    public void set(T value) {
        this.value.set(value);
    }

    @Override
    public T get() {
        T current = value.get();
        if (current == null) {
            throw new IllegalStateException("Registry object has not been registered: " + id);
        }
        return current;
    }
}
