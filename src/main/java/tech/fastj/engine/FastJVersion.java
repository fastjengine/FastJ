package tech.fastj.engine;

import java.util.Arrays;

public enum FastJVersion {
    Legacy(true, 1, 5, 1, 1),
    Current(false, 1, 6, 0, 0),
    Newer(true, 1, 6, 1, 1);

    private final boolean isExperimental;
    private final int majorVersion;
    private final int minorVersion;
    private final int currentPatchVersion;
    private final int[] patchVersions;

    FastJVersion(boolean experimental, int maj, int min, int currentPatch, int... patches) {
        isExperimental = experimental;
        majorVersion = maj;
        minorVersion = min;
        currentPatchVersion = currentPatch;
        patchVersions = patches;
    }

    public boolean isExperimental() {
        return isExperimental;
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

    public FastJVersion of(int majorVersion, int minorVersion, int patchVersion) {
        for (FastJVersion version : values()) {
            if (version.majorVersion != majorVersion) {
                continue;
            }
            if (version.minorVersion != minorVersion) {
                continue;
            }
            if (version.currentPatchVersion != patchVersion) {
                continue;
            }
            return version;
        }

        if (Newer.majorVersion > majorVersion) {
            return Legacy;
        }
        if (Newer.minorVersion > minorVersion) {
            return Legacy;
        }
        if (Newer.currentPatchVersion > patchVersion) {
            return Legacy;
        }
        return Newer;
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

    @Override
    public String toString() {
        return majorVersion + "." + minorVersion + "." + currentPatchVersion;
    }

    public String extendedToString() {
        return "FastJ Version " + majorVersion + "." + minorVersion + "." + currentPatchVersion + (isExperimental ? " (Experimental)" : "");
    }
}
