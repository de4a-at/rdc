package com.helger.dcng.core.api;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.concurrent.SimpleReadWriteLock;
import com.helger.dcng.api.dd.IDDServiceGroupHrefProvider;
import com.helger.dcng.api.dd.IDDServiceMetadataProvider;
import com.helger.dcng.api.ial.IIALClient;
import com.helger.dcng.core.ial.DcngIALClientRemote;
import com.helger.dcng.core.smp.DDServiceGroupHrefProviderSMP;
import com.helger.dcng.core.smp.DDServiceMetadataProviderSMP;

/**
 * Global DCNG API configuration.<br>
 * This configuration is e.g. changed by the Simulator to install "mock"
 * handler.
 *
 * @author Philip Helger
 */
@ThreadSafe
public final class DcngApiConfig
{
  private static final SimpleReadWriteLock RW_LOCK = new SimpleReadWriteLock ();

  @GuardedBy ("RW_LOCK")
  private static IDDServiceGroupHrefProvider s_aDDSGHrefProvider = new DDServiceGroupHrefProviderSMP ();
  @GuardedBy ("RW_LOCK")
  private static IDDServiceMetadataProvider s_aDDSMProvider = new DDServiceMetadataProviderSMP ();
  @GuardedBy ("RW_LOCK")
  private static IIALClient s_aIALClient = DcngIALClientRemote.createDefaultInstance ();

  private DcngApiConfig ()
  {}

  @Nonnull
  public static IDDServiceGroupHrefProvider getDDServiceGroupHrefProvider ()
  {
    return RW_LOCK.readLockedGet ( () -> s_aDDSGHrefProvider);
  }

  public static void setDDServiceGroupHrefProvider (@Nonnull final IDDServiceGroupHrefProvider aProvider)
  {
    ValueEnforcer.notNull (aProvider, "Provider");
    RW_LOCK.writeLocked ( () -> s_aDDSGHrefProvider = aProvider);
  }

  @Nonnull
  public static IDDServiceMetadataProvider getDDServiceMetadataProvider ()
  {
    return RW_LOCK.readLockedGet ( () -> s_aDDSMProvider);
  }

  public static void setDDServiceMetadataProvider (@Nonnull final IDDServiceMetadataProvider aProvider)
  {
    ValueEnforcer.notNull (aProvider, "Provider");
    RW_LOCK.writeLocked ( () -> s_aDDSMProvider = aProvider);
  }

  @Nonnull
  public static IIALClient getIALClient ()
  {
    return RW_LOCK.readLockedGet ( () -> s_aIALClient);
  }

  public static void setIALClient (@Nonnull final IIALClient aIALClient)
  {
    ValueEnforcer.notNull (aIALClient, "IALClient");
    RW_LOCK.writeLocked ( () -> s_aIALClient = aIALClient);
  }
}
