/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.HashMap
 */
package dev.gether.getconfig.serializer;

import dev.gether.getconfig.domain.Cuboid;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.HashMap;

public class CuboidSerializer
extends StdSerializer<Cuboid> {
    public CuboidSerializer() {
        super(Cuboid.class);
    }

    @Override
    public void serialize(Cuboid cuboid, JsonGenerator gen, SerializerProvider provider) throws IOException {
        HashMap itemMap = new HashMap();
        itemMap.put((Object)"world", (Object)cuboid.getWorldName());
        itemMap.put((Object)"min-x", (Object)cuboid.getMinX());
        itemMap.put((Object)"min-y", (Object)cuboid.getMinY());
        itemMap.put((Object)"min-z", (Object)cuboid.getMinZ());
        itemMap.put((Object)"max-x", (Object)cuboid.getMaxX());
        itemMap.put((Object)"max-y", (Object)cuboid.getMaxY());
        itemMap.put((Object)"max-z", (Object)cuboid.getMaxZ());
        gen.writeObject(itemMap);
    }
}

