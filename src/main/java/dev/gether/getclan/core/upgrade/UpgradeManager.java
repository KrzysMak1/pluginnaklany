/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.Optional
 */
package dev.gether.getclan.core.upgrade;

import dev.gether.getclan.config.FileManager;
import dev.gether.getclan.core.upgrade.Upgrade;
import dev.gether.getclan.core.upgrade.UpgradeCost;
import dev.gether.getclan.core.upgrade.UpgradeType;
import java.util.Optional;

public class UpgradeManager {
    private final FileManager fileManager;

    public UpgradeManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public Optional<UpgradeCost> findUpgradeCost(UpgradeType upgradeType, int level) {
        Optional<Upgrade> upgradeByType = this.fileManager.getUpgradesConfig().findUpgradeByType(upgradeType);
        if (upgradeByType.isEmpty()) {
            return Optional.empty();
        }
        Upgrade upgrade = upgradeByType.get();
        UpgradeCost upgradeCost = upgrade.getUpgradesCost().get(level);
        if (upgradeCost == null) {
            return Optional.empty();
        }
        return Optional.of(upgradeCost);
    }

    public void save() {
        this.fileManager.getUpgradesConfig().save();
    }
}
