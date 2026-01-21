/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.Map
 *  org.bukkit.Location
 */
package dev.gether.getconfig.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.Map;
import org.bukkit.Location;

public class LocationSerializer
extends StdSerializer<Location> {
    public LocationSerializer() {
        super(Location.class);
    }

    @Override
    public void serialize(Location value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Map serializedItemStack = value.serialize();
        gen.writeObject(serializedItemStack);
    }
}

