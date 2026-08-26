package net.anvian.gradle;

import org.gradle.api.artifacts.transform.CacheableTransform;
import org.gradle.api.artifacts.transform.InputArtifact;
import org.gradle.api.artifacts.transform.TransformAction;
import org.gradle.api.artifacts.transform.TransformOutputs;
import org.gradle.api.artifacts.transform.TransformParameters;
import org.gradle.api.file.FileSystemLocation;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Classpath;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * Removes build-tool-only Loom metadata from the published Anvian Fabric jar.
 *
 * The jar is otherwise consumed unchanged and remains in intermediary mapping
 * namespace for Loom to remap. This is needed because the 1.20 artifact was
 * published by Loom 1.2.8, while this 1.20.5 project uses the Gradle-8.7
 * compatible Loom 1.6.12 from the migration template.
 */
@CacheableTransform
public abstract class StripFabricLoomVersion implements TransformAction<TransformParameters.None> {
    @Classpath
    @InputArtifact
    public abstract Provider<FileSystemLocation> getInputArtifact();

    @Override
    public void transform(TransformOutputs outputs) {
        File input = getInputArtifact().get().getAsFile();
        File output = outputs.file(input.getName());

        try (JarFile inputJar = new JarFile(input);
             FileOutputStream fileOutput = new FileOutputStream(output)) {
            Manifest manifest = inputJar.getManifest();
            if (manifest != null) {
                Attributes mainAttributes = manifest.getMainAttributes();
                mainAttributes.remove(new Attributes.Name("Fabric-Loom-Version"));
            }

            try (JarOutputStream outputJar = manifest == null
                    ? new JarOutputStream(fileOutput)
                    : new JarOutputStream(fileOutput, manifest)) {
                inputJar.stream().forEach(entry -> copyEntry(inputJar, outputJar, entry));
            }
        } catch (IOException exception) {
            throw new RuntimeException("Unable to sanitize Fabric Loom metadata in " + input, exception);
        }
    }

    private static void copyEntry(JarFile inputJar, JarOutputStream outputJar, JarEntry source) {
        if (source.getName().equalsIgnoreCase("META-INF/MANIFEST.MF")) {
            return;
        }

        try {
            outputJar.putNextEntry(new JarEntry(source));
            if (!source.isDirectory()) {
                try (var input = inputJar.getInputStream(source)) {
                    input.transferTo(outputJar);
                }
            }
            outputJar.closeEntry();
        } catch (IOException exception) {
            throw new RuntimeException("Unable to copy entry " + source.getName(), exception);
        }
    }
}
