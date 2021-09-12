package tech.fastj.engine;

import java.util.Arrays;

public enum FastJVersion {
    LegacyVersion(true, 1, 5, 1, 1),
    CurrentVersion(false, 1, 6, 0, 0),
    NewerVersion(false, 1, 6, 1, 0);

    private final boolean isStable;
    private final int majorVersion;
    private final int minorVersion;
    private final int currentPatchVersion;
    private final int[] patchVersions;

    FastJVersion(boolean stable, int min, int maj, int currentPatch, int... patches) {
        isStable = stable;
        majorVersion = maj;
        minorVersion = min;
        currentPatchVersion = currentPatch;
        patchVersions = patches;
    }

    public boolean isStable() {
        return isStable;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getCurrentPatchVersion() {
        return currentPatchVersion;
    }

    public int[] getPatchVersions() {
        return Arrays.copyOf(patchVersions, patchVersions.length);
    }

    public FastJVersion compare(FastJVersion version, FastJVersion otherVersion) {
        return compareMajor(version, otherVersion);
    }

    private FastJVersion compareMajor(FastJVersion version, FastJVersion otherVersion) {
        int majorComparison = Integer.compare(version.majorVersion, otherVersion.majorVersion);
        switch (majorComparison) {
            case 1: {
                return version;
            }
            case -1: {
                return otherVersion;
            }
            case 0: {
                return compareMinor(version, otherVersion);
            }
            default: {
                throw new IllegalArgumentException("Invalid major version(s): " + version.majorVersion + ", " + otherVersion.majorVersion + ".");
            }
        }
    }

    private FastJVersion compareMinor(FastJVersion version, FastJVersion otherVersion) {
        int minorComparison = Integer.compare(version.minorVersion, otherVersion.minorVersion);
        switch (minorComparison) {
            case 1: {
                return version;
            }
            case -1: {
                return otherVersion;
            }
            case 0: {
                return comparePatch(version, otherVersion);
            }
            default: {
                throw new IllegalArgumentException("Invalid minor version(s): " + version.minorVersion + ", " + otherVersion.minorVersion + ".");
            }
        }
    }

    private FastJVersion comparePatch(FastJVersion version, FastJVersion otherVersion) {
        int patchComparison = Integer.compare(version.currentPatchVersion, otherVersion.currentPatchVersion);
        switch (patchComparison) {
            case 1:
            case 0: {
                return version;
            }
            case -1: {
                return otherVersion;
            }
            default: {
                throw new IllegalArgumentException("Invalid patch version(s:) " + version.currentPatchVersion + ", " + otherVersion.currentPatchVersion + ".");
            }
        }
    }
}
