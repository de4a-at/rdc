package com.helger.dcng.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.simple.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.peppolid.simple.process.SimpleProcessIdentifier;

/**
 * A special {@link IIdentifierFactory} for DE4A.
 *
 * @author Philip Helger
 */
public final class DcngIdentifierFactory implements IIdentifierFactory
{
  public static final String DOCTYPE_SCHEME_CANONICAL_EVIDENCE = "urn:de4a-eu:CanonicalEvidenceType";
  public static final String DOCTYPE_SCHEME_CANONICAL_EVENT_CATALOGUE = "urn:de4a-eu:CanonicalEventCatalogueType";
  public static final String PARTICIPANT_SCHEME = "iso6523-actorid-upis";
  public static final String PROCESS_SCHEME = "urn:de4a-eu:MessageType";

  // Use DcngConfig.getIdentifierFactory () for access
  static final DcngIdentifierFactory INSTANCE = new DcngIdentifierFactory ();

  private DcngIdentifierFactory ()
  {}

  @Nullable
  private static String _nullNotEmptyTrimmed (@Nullable final String s)
  {
    if (s == null)
      return null;
    final String ret = s.trim ();
    return ret.length () == 0 ? null : ret;
  }

  @Override
  public boolean isDocumentTypeIdentifierSchemeMandatory ()
  {
    return true;
  }

  @Nonnull
  @Override
  public String getDefaultDocumentTypeIdentifierScheme ()
  {
    return DOCTYPE_SCHEME_CANONICAL_EVIDENCE;
  }

  @Override
  @Nullable
  public SimpleDocumentTypeIdentifier createDocumentTypeIdentifier (@Nullable final String sScheme,
                                                                    @Nullable final String sValue)
  {
    final String sRealValue = _nullNotEmptyTrimmed (isDocumentTypeIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue)
                                                                                                      : sValue);
    if (sRealValue == null)
      return null;
    return new SimpleDocumentTypeIdentifier (_nullNotEmptyTrimmed (sScheme), sRealValue);
  }

  @Override
  public boolean isParticipantIdentifierSchemeMandatory ()
  {
    return true;
  }

  @Nonnull
  @Override
  public String getDefaultParticipantIdentifierScheme ()
  {
    return PARTICIPANT_SCHEME;
  }

  @Override
  @Nullable
  public SimpleParticipantIdentifier createParticipantIdentifier (@Nullable final String sScheme,
                                                                  @Nullable final String sValue)
  {
    final String sRealValue = _nullNotEmptyTrimmed (isParticipantIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue)
                                                                                                     : sValue);
    if (sRealValue == null)
      return null;
    return new SimpleParticipantIdentifier (_nullNotEmptyTrimmed (sScheme), sRealValue);
  }

  @Override
  public boolean isProcessIdentifierSchemeMandatory ()
  {
    return true;
  }

  @Nonnull
  @Override
  public String getDefaultProcessIdentifierScheme ()
  {
    return PROCESS_SCHEME;
  }

  @Override
  @Nullable
  public SimpleProcessIdentifier createProcessIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final String sRealValue = _nullNotEmptyTrimmed (isProcessIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue)
                                                                                                 : sValue);
    if (sRealValue == null)
      return null;
    return new SimpleProcessIdentifier (_nullNotEmptyTrimmed (sScheme), sRealValue);
  }
}
