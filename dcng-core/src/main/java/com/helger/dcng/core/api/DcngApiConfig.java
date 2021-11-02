/*
 * Copyright (C) 2021 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.dcng.core.api;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.GuardedBy;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.concurrent.SimpleReadWriteLock;
import com.helger.dcng.api.dd.IDDServiceGroupHrefProvider;
import com.helger.dcng.api.dd.IDDServiceMetadataProvider;
import com.helger.dcng.core.smp.DDServiceGroupHrefProviderSMP;
import com.helger.dcng.core.smp.DDServiceMetadataProviderSMP;

/**
 * Global RDC API configuration.<br>
 * This configuration is e.g. changed by the Simulator to install "mock"
 * handler.
 *
 * @author Philip Helger
 */
public final class DcngApiConfig
{
  private static final SimpleReadWriteLock RW_LOCK = new SimpleReadWriteLock ();

  @GuardedBy ("RW_LOCK")
  private static IDDServiceGroupHrefProvider s_aDDSGHrefProvider = new DDServiceGroupHrefProviderSMP ();
  @GuardedBy ("RW_LOCK")
  private static IDDServiceMetadataProvider s_aDDSMProvider = new DDServiceMetadataProviderSMP ();

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
}