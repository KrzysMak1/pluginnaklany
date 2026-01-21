/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.io.BufferedReader
 *  java.io.File
 *  java.io.FileReader
 *  java.io.IOException
 *  java.io.Reader
 *  java.lang.Class
 *  java.lang.IllegalAccessException
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.lang.reflect.Field
 *  java.lang.reflect.InvocationTargetException
 *  java.lang.reflect.Method
 *  java.net.URI
 *  java.nio.charset.StandardCharsets
 *  java.nio.file.Files
 *  java.nio.file.OpenOption
 *  java.nio.file.Path
 *  java.nio.file.Paths
 *  java.util.LinkedHashMap
 *  java.util.Map$Entry
 *  org.bukkit.Color
 *  org.bukkit.Location
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getconfig;

import dev.gether.getconfig.annotation.Comment;
import dev.gether.getconfig.annotation.Init;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import dev.gether.getconfig.deserializer.ColorDeserializer;
import dev.gether.getconfig.deserializer.CuboidDeserializer;
import dev.gether.getconfig.deserializer.ItemModelDataDeserializer;
import dev.gether.getconfig.deserializer.ItemStackDeserializer;
import dev.gether.getconfig.deserializer.LocationDeserializer;
import dev.gether.getconfig.domain.Cuboid;
import dev.gether.getconfig.domain.ItemModelData;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.gether.getconfig.serializer.ColorSerializer;
import dev.gether.getconfig.serializer.CuboidSerializer;
import dev.gether.getconfig.serializer.ItemModelDataSerializer;
import dev.gether.getconfig.serializer.ItemStackSerializer;
import dev.gether.getconfig.serializer.LocationSerializer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class GetConfig {
    private ObjectMapper mapper;
    private File file;

    public GetConfig() {
        YAMLFactory yamlFactory = new YAMLFactory();
        yamlFactory.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        this.mapper = new ObjectMapper(yamlFactory);
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        module.addSerializer(ItemStack.class, new ItemStackSerializer());
        module.addSerializer(Location.class, new LocationSerializer());
        module.addSerializer(Color.class, new ColorSerializer());
        module.addSerializer(ItemModelData.class, new ItemModelDataSerializer());
        module.addSerializer(Cuboid.class, new CuboidSerializer());
        module.addDeserializer(ItemStack.class, new ItemStackDeserializer());
        module.addDeserializer(Location.class, new LocationDeserializer());
        module.addDeserializer(ItemModelData.class, new ItemModelDataDeserializer());
        module.addDeserializer(Cuboid.class, new CuboidDeserializer());
        module.addDeserializer(Color.class, new ColorDeserializer());
        this.mapper.registerModule(module);
    }

    public void file(File file) {
        this.file = file;
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                throw new RuntimeException((Throwable)e);
            }
        }
    }

    public void save() {
        if (this.file == null) {
            throw new IllegalStateException("File has not been created.");
        }
        try {
            String yamlString = this.mapper.writeValueAsString(this);
            String processedYaml = this.insertComments(yamlString);
            Files.write((Path)Paths.get((URI)this.file.toURI()), (byte[])processedYaml.getBytes(StandardCharsets.UTF_8), (OpenOption[])new OpenOption[0]);
        }
        catch (IOException ex) {
            throw new RuntimeException((Throwable)ex);
        }
    }

    private String insertComments(String yamlString) {
        LinkedHashMap<String, String[]> comments = new LinkedHashMap<>();
        for (Field field : this.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(Comment.class)) continue;
            Comment comment = (Comment)field.getAnnotation(Comment.class);
            comments.put(field.getName(), comment.value());
        }
        StringBuilder sb = new StringBuilder();
        for (String line : yamlString.split("\n")) {
            for (Map.Entry<String, String[]> entry : comments.entrySet()) {
                if (!line.startsWith(entry.getKey() + ":")) continue;
                for (String commentLine : entry.getValue()) {
                    sb.append("# ").append(commentLine).append("\n");
                }
            }
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public void load() {
        if (this.file == null) {
            throw new IllegalStateException("The file was not created");
        }
        try {
            if (!this.file.exists() || this.isFileEmpty(this.file)) {
                this.save();
                return;
            }
            this.mapper.readerForUpdating(this).readValue(this.file);
        }
        catch (IOException ex) {
            throw new RuntimeException((Throwable)ex);
        }
    }

    public void init(Object[] objects) {
        for (int i = 0; i < objects.length; ++i) {
            Object object = objects[i];
            Class clazz = object.getClass();
            for (Method method : clazz.getDeclaredMethods()) {
                if (!method.isAnnotationPresent(Init.class)) continue;
                try {
                    method.setAccessible(true);
                    method.invoke(object, new Object[0]);
                }
                catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isFileEmpty(File file) throws IOException {
        try (BufferedReader br = new BufferedReader((Reader)new FileReader(file));){
            boolean bl = br.readLine() == null;
            return bl;
        }
    }
}
