package org.pentaho.reporting.platform.plugin;

import java.io.OutputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.platform.api.engine.IParameterProvider;
import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.api.repository.ISchedule;
import org.pentaho.platform.api.repository.ISubscribeContent;
import org.pentaho.platform.api.repository.ISubscription;
import org.pentaho.platform.api.repository.ISubscriptionRepository;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.engine.services.WebServiceUtil;
import org.pentaho.reporting.engine.classic.core.AttributeNames;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.ReportDataFactoryException;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfPageableModule;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.plaintext.PlainTextPageableModule;
import org.pentaho.reporting.engine.classic.core.modules.output.table.csv.CSVTableModule;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.HtmlTableModule;
import org.pentaho.reporting.engine.classic.core.modules.output.table.rtf.RTFTableModule;
import org.pentaho.reporting.engine.classic.core.modules.output.table.xls.ExcelTableModule;
import org.pentaho.reporting.engine.classic.core.parameters.AbstractParameter;
import org.pentaho.reporting.engine.classic.core.parameters.DefaultParameterContext;
import org.pentaho.reporting.engine.classic.core.parameters.ListParameter;
import org.pentaho.reporting.engine.classic.core.parameters.ParameterAttributeNames;
import org.pentaho.reporting.engine.classic.core.parameters.ParameterContext;
import org.pentaho.reporting.engine.classic.core.parameters.ParameterDefinitionEntry;
import org.pentaho.reporting.engine.classic.core.parameters.ParameterUtils;
import org.pentaho.reporting.engine.classic.core.parameters.ParameterValues;
import org.pentaho.reporting.engine.classic.core.parameters.PlainParameter;
import org.pentaho.reporting.engine.classic.core.parameters.ReportParameterDefinition;
import org.pentaho.reporting.engine.classic.core.parameters.StaticListParameter;
import org.pentaho.reporting.engine.classic.core.parameters.ValidationMessage;
import org.pentaho.reporting.engine.classic.core.parameters.ValidationResult;
import org.pentaho.reporting.engine.classic.core.util.NullOutputStream;
import org.pentaho.reporting.engine.classic.core.util.beans.BeanException;
import org.pentaho.reporting.engine.classic.core.util.beans.ConverterRegistry;
import org.pentaho.reporting.engine.classic.core.util.beans.ValueConverter;
import org.pentaho.reporting.libraries.base.util.StringUtils;
import org.pentaho.reporting.platform.plugin.messages.Messages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Todo: Document me!
 * <p/>
 * Date: 22.07.2010
 * Time: 16:24:30
 *
 * @author Thomas Morgner.
 */
public class ParameterXmlContentHandler
{
  private static final Log logger = LogFactory.getLog(ParameterXmlContentHandler.class);

  private Map<String, ParameterDefinitionEntry> systemParameter;

  private ReportContentGenerator contentGenerator;
  private Document document;
  //private ParameterContext parameterContext;
  private IParameterProvider requestParameters;
  private IPentahoSession userSession;
  private Map<String, Object> inputs;
  private String reportDefinitionPath;

  private static final String SYS_PARAM_RENDER_MODE = "renderMode";
  private static final String SYS_PARAM_OUTPUT_TARGET = SimpleReportingComponent.OUTPUT_TARGET;
  private static final String SYS_PARAM_SUBSCRIPTION_NAME = "subscription-name";
  private static final String SYS_PARAM_DESTINATION = "destination";
  private static final String SYS_PARAM_SCHEDULE_ID = "schedule-id";
  private static final String GROUP_SUBSCRIPTION = "subscription";
  private static final String GROUP_SYSTEM = "system";
  private static final String GROUP_PARAMETERS = "parameters";

  public ParameterXmlContentHandler(final ReportContentGenerator contentGenerator)
  {
    this.contentGenerator = contentGenerator;
    this.inputs = contentGenerator.createInputs();
    this.requestParameters = contentGenerator.getRequestParameters();
    this.userSession = contentGenerator.getUserSession();
  }

  public IParameterProvider getRequestParameters()
  {
    return requestParameters;
  }

  private Map<String, ParameterDefinitionEntry> getSystemParameter()
  {
    if (systemParameter == null)
    {
      final Map<String, ParameterDefinitionEntry> parameter = new LinkedHashMap<String, ParameterDefinitionEntry>();
      parameter.put(SYS_PARAM_SUBSCRIPTION_NAME, createSubscriptionNameParameter());
      parameter.put(SYS_PARAM_DESTINATION, createDestinationParameter());
      parameter.put(SYS_PARAM_SCHEDULE_ID, createScheduleIdParameter());
      parameter.put(SYS_PARAM_OUTPUT_TARGET, createOutputParameter());
      parameter.put("subscribe", createGenericBooleanSystemParameter("subscribe", false)); // NON-NLS

      parameter.put("solution", createGenericSystemParameter("solution", false)); // NON-NLS
      parameter.put("yield-rate", createGenericIntSystemParameter("yield-rate", false)); // NON-NLS
      parameter.put("accepted-page", createGenericIntSystemParameter("accepted-page", false)); // NON-NLS
      parameter.put("path", createGenericSystemParameter("path", false)); // NON-NLS
      parameter.put("name", createGenericSystemParameter("name", false)); // NON-NLS
      parameter.put("action", createGenericSystemParameter("action", true)); // NON-NLS
      parameter.put("output-type", createGenericSystemParameter("output-type", true)); // NON-NLS
      parameter.put("layout", createGenericSystemParameter("layout", true)); // NON-NLS
      parameter.put("content-handler-pattern", createGenericSystemParameter("content-handler-pattern", true)); // NON-NLS
      parameter.put("autoSubmit", createGenericBooleanSystemParameter("autoSubmit", true)); // NON-NLS
      parameter.put("autoSubmitUI", createGenericBooleanSystemParameter("autoSubmitUI", true)); // NON-NLS
      parameter.put("dashboard-mode", createGenericBooleanSystemParameter("dashboard-mode", false)); // NON-NLS
      parameter.put("showParameters", createGenericBooleanSystemParameter("showParameters", true)); // NON-NLS
      parameter.put("paginate", createGenericBooleanSystemParameter("paginate", true)); // NON-NLS
      parameter.put("ignoreDefaultDates", createGenericBooleanSystemParameter("ignoreDefaultDates", true)); // NON-NLS
      parameter.put("print", createGenericBooleanSystemParameter("print", false)); // NON-NLS
      parameter.put("printer-name", createGenericSystemParameter("printer-name", false)); // NON-NLS
      parameter.put("renderMode", createRenderModeSystemParameter()); // NON-NLS

      systemParameter = Collections.unmodifiableMap(parameter);
    }

    return systemParameter;
  }

  /**
   * Defines whether parameter with display-type "datepicker" that have no default value set shall
   * default to "today". This setting generates a default value for the parameter UI, but has no effect
   * otherwise. It is flawed from the very beginning and should not be used.
   *
   * @return whether we generate default dates.
   */
  private boolean isGenerateDefaultDates()
  {
    final Object value = inputs.get("ignoreDefaultDates"); // NON-NLS
    if (value == null)
    {
      // we do not generate default dates until it is explicitly requested.
      // if the users want default values for parameters then let them define those in the parameter
      return false;
    }

    return "true".equals(value);
  }

  public void createParameterContent(final OutputStream outputStream,
                                     final String reportDefinitionPath) throws Exception
  {
    this.reportDefinitionPath = reportDefinitionPath;
    this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

    final IParameterProvider requestParams = getRequestParameters();

    final boolean subscribe = "true".equals(requestParams.getStringParameter("subscribe", "false")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    // handle parameter feedback (XML) services

    final SimpleReportingComponent reportComponent = new SimpleReportingComponent();
    reportComponent.setSession(userSession);
    reportComponent.setReportDefinitionPath(reportDefinitionPath);
    reportComponent.setPaginateOutput(true);
    reportComponent.setDefaultOutputTarget(HtmlTableModule.TABLE_HTML_PAGE_EXPORT_TYPE);
    reportComponent.setInputs(inputs);

    final MasterReport report = reportComponent.getReport();

    final DefaultParameterContext parameterContext = new DefaultParameterContext(report);
    try
    {
      // open parameter context
      parameterContext.open();
      // apply inputs to parameters
      ValidationResult validationResult = new ValidationResult();
      validationResult = reportComponent.applyInputsToReportParameters(parameterContext, validationResult);

      final ReportParameterDefinition reportParameterDefinition = report.getParameterDefinition();
      final ValidationResult vr = reportParameterDefinition.getValidator().validate
          (validationResult, reportParameterDefinition, parameterContext);

      final Element parameters = document.createElement(GROUP_PARAMETERS); //$NON-NLS-1$
      parameters.setAttribute("is-prompt-needed", String.valueOf(vr.isEmpty() == false)); //$NON-NLS-1$ //$NON-NLS-2$
      parameters.setAttribute("subscribe", String.valueOf(subscribe)); //$NON-NLS-1$ //$NON-NLS-2$

      // check if pagination is allowed and turned on
      if (vr.isEmpty()) //$NON-NLS-1$ //$NON-NLS-2$
      {
        appendPageCount(reportComponent, parameters);
      }

      final Boolean autoSubmitFlag = requestFlag("autoSubmit", report,
          AttributeNames.Core.NAMESPACE, AttributeNames.Core.AUTO_SUBMIT_PARAMETER,
          "org.pentaho.reporting.engine.classic.core.ParameterAutoSubmit");
      if (Boolean.TRUE.equals(autoSubmitFlag))
      {
        parameters.setAttribute("autoSubmit", "true");
      }
      else if (Boolean.FALSE.equals(autoSubmitFlag))
      {
        parameters.setAttribute("autoSubmit", "false");
      }

      final Boolean autoSubmitUiFlag = requestFlag("autoSubmitUI", report, // NON-NLS
          AttributeNames.Core.NAMESPACE, AttributeNames.Core.AUTO_SUBMIT_DEFAULT,
          "org.pentaho.reporting.engine.classic.core.ParameterAutoSubmitUI");
      if (Boolean.FALSE.equals(autoSubmitUiFlag))
      {
        parameters.setAttribute("autoSubmitUI", "false"); // NON-NLS
      }
      else
      {
        parameters.setAttribute("autoSubmitUI", "true"); // NON-NLS
      }

      final Boolean showParameterUI = requestFlag("showParameterUI", report, // NON-NLS
          AttributeNames.Core.NAMESPACE, AttributeNames.Core.SHOW_PARAMETER_UI, null);
      if (Boolean.FALSE.equals(showParameterUI))
      {
        parameters.setAttribute("show-parameter-ui", "false"); // NON-NLS
      }
      else
      {
        parameters.setAttribute("show-parameter-ui", "true"); // NON-NLS
      }

      parameters.setAttribute("layout", requestConfiguration("layout", report, // NON-NLS
          AttributeNames.Core.NAMESPACE, AttributeNames.Core.PARAMETER_UI_LAYOUT,
          "org.pentaho.reporting.engine.classic.core.ParameterUiLayout"));

      final LinkedHashMap<String, ParameterDefinitionEntry> reportParameters =
          new LinkedHashMap<String, ParameterDefinitionEntry>();
      final ParameterDefinitionEntry[] parameterDefinitions = reportParameterDefinition.getParameterDefinitions();
      for (final ParameterDefinitionEntry parameter : parameterDefinitions)
      {
        reportParameters.put(parameter.getName(), parameter);
      }
      reportParameters.putAll(getSystemParameter());

      hideOutputParameterIfLocked(report, reportParameters);
      hideSubscriptionParameter(subscribe, reportParameters);
      final Map<String, Object> inputs = computeRealInput
          (parameterContext, reportParameters, reportComponent.getComputedOutputTarget());

      for (final ParameterDefinitionEntry parameter : reportParameters.values())
      {
        final Object selections = inputs.get(parameter.getName());
        parameters.appendChild(createParameterElement(parameter, parameterContext, selections));
      }

      if (vr.isEmpty() == false)
      {
        parameters.appendChild(createErrorElements(vr));
      }

      document.appendChild(parameters);

      final DOMSource source = new DOMSource(document);
      final StreamResult result = new StreamResult(outputStream);
      final Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.transform(source, result);
      // close parameter context
    }
    finally
    {
      parameterContext.close();
    }
  }

  private Map<String, Object> computeRealInput(final ParameterContext parameterContext,
                                               final LinkedHashMap<String, ParameterDefinitionEntry> reportParameters,
                                               final String computedOutputTarget)
  {
    final Map<String, Object> realInputs = new HashMap<String, Object>();
    realInputs.put(SYS_PARAM_DESTINATION, lookupDestination());
    realInputs.put(SYS_PARAM_SCHEDULE_ID, lookupSchedules());
    realInputs.put(SYS_PARAM_SUBSCRIPTION_NAME, lookupSubscriptionName());

    for (final ParameterDefinitionEntry parameter : reportParameters.values())
    {
      final Object value = inputs.get(parameter.getName());
      try
      {
        final Object translatedValue = ReportContentUtil.computeParameterValue(parameterContext, parameter, value);
        realInputs.put(parameter.getName(), translatedValue);
      }
      catch (Exception be)
      {
        if (logger.isDebugEnabled())
        {
          logger.debug(Messages.getString
              ("ReportPlugin.debugParameterCannotBeConverted", parameter.getName(), String.valueOf(value)), be);
        }
      }
    }

    // thou cannot override the output target with invalid values ..
    realInputs.put(SYS_PARAM_OUTPUT_TARGET, computedOutputTarget);
    return realInputs;
  }

  private void hideOutputParameterIfLocked(final MasterReport report,
                                           final Map<String, ParameterDefinitionEntry> reportParameters)
  {
    final boolean lockOutputType = Boolean.TRUE.equals(report.getAttribute
        (AttributeNames.Core.NAMESPACE, AttributeNames.Core.LOCK_PREFERRED_OUTPUT_TYPE));
    final ParameterDefinitionEntry definitionEntry = reportParameters.get(SimpleReportingComponent.OUTPUT_TARGET);
    if (definitionEntry instanceof AbstractParameter)
    {
      final AbstractParameter parameter = (AbstractParameter) definitionEntry;
      parameter.setHidden(lockOutputType);
      parameter.setMandatory(!lockOutputType);
    }
  }


  private Element createParameterElement(final ParameterDefinitionEntry parameter,
                                         final ParameterContext parameterContext,
                                         final Object selections) throws BeanException, ReportDataFactoryException
  {
    try
    {
      final Element parameterElement = document.createElement("parameter"); //$NON-NLS-1$
      parameterElement.setAttribute("name", parameter.getName()); //$NON-NLS-1$
      final Class valueType = parameter.getValueType();
      parameterElement.setAttribute("type", valueType.getName()); //$NON-NLS-1$
      parameterElement.setAttribute("is-mandatory", String.valueOf(parameter.isMandatory())); //$NON-NLS-1$ //$NON-NLS-2$

      final String[] namespaces = parameter.getParameterAttributeNamespaces();
      for (int i = 0; i < namespaces.length; i++)
      {
        final String namespace = namespaces[i];
        final String[] attributeNames = parameter.getParameterAttributeNames(namespace);
        for (final String attributeName : attributeNames)
        {
          final String attributeValue = parameter.getParameterAttribute
              (namespace, attributeName, parameterContext);
          // expecting: label, parameter-render-type, parameter-layout
          // but others possible as well, so we set them all
          final Element attributeElement = document.createElement("attribute"); // NON-NLS
          attributeElement.setAttribute("namespace", namespace); // NON-NLS
          attributeElement.setAttribute("name", attributeName); // NON-NLS
          attributeElement.setAttribute("value", attributeValue); // NON-NLS

          parameterElement.appendChild(attributeElement);
        }
      }

      final Class elementValueType;
      if (valueType.isArray())
      {
        elementValueType = valueType.getComponentType();
      }
      else
      {
        elementValueType = valueType;
      }

      if (Date.class.isAssignableFrom(elementValueType))
      {
        parameterElement.setAttribute("timzone-hint", computeTimeZoneHint(parameter, parameterContext));
      }

      final HashSet<Object> selectionSet = new HashSet<Object>();
      if (selections != null)
      {
        if (selections.getClass().isArray())
        {
          final int length = Array.getLength(selections);
          for (int i = 0; i < length; i++)
          {
            final Object value = Array.get(selections, i);
            selectionSet.add(value);
          }
        }
        else
        {
          selectionSet.add(selections);
        }
      }
      else
      {
        final String type = parameter.getParameterAttribute
            (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.TYPE, parameterContext);
        if (ParameterAttributeNames.Core.TYPE_DATEPICKER.equals(type) &&
            Date.class.isAssignableFrom(valueType))
        {
          if (isGenerateDefaultDates())
          {
            selectionSet.add(new Date());
          }
        }
      }

      if (parameter instanceof ListParameter)
      {
        final ListParameter asListParam = (ListParameter) parameter;
        parameterElement.setAttribute("is-multi-select", String.valueOf(asListParam.isAllowMultiSelection())); //$NON-NLS-1$ //$NON-NLS-2$
        parameterElement.setAttribute("is-strict", String.valueOf(asListParam.isStrictValueCheck())); //$NON-NLS-1$ //$NON-NLS-2$

        final Element valuesElement = document.createElement("values"); //$NON-NLS-1$
        parameterElement.appendChild(valuesElement);

        final ParameterValues possibleValues = asListParam.getValues(parameterContext);
        for (int i = 0; i < possibleValues.getRowCount(); i++)
        {
          final Object key = possibleValues.getKeyValue(i);
          final Object value = possibleValues.getTextValue(i);

          if (key == null)
          {
            continue;
          }

          final Element valueElement = document.createElement("value"); //$NON-NLS-1$
          valuesElement.appendChild(valueElement);

          valueElement.setAttribute("label", String.valueOf(value)); //$NON-NLS-1$ //$NON-NLS-2$
          valueElement.setAttribute("type", elementValueType.getName()); //$NON-NLS-1$
          valueElement.setAttribute("selected", String.valueOf(selectionSet.contains(value)));//$NON-NLS-1$

          if (value == null)
          {
            valueElement.setAttribute("null", "true"); //$NON-NLS-1$ //$NON-NLS-2$
          }
          else
          {
            valueElement.setAttribute("null", "false"); //$NON-NLS-1$ //$NON-NLS-2$
            valueElement.setAttribute("value",
                convertParameterValueToString(parameter, parameterContext, key, elementValueType)); //$NON-NLS-1$ //$NON-NLS-2$
          }
        }
      }
      else if (parameter instanceof PlainParameter)
      {
        // apply defaults, this is the easy case
        parameterElement.setAttribute("is-multi-select", "false"); //$NON-NLS-1$ //$NON-NLS-2$
        parameterElement.setAttribute("is-strict", "false"); //$NON-NLS-1$ //$NON-NLS-2$

        if (selections != null)
        {
          final Element valuesElement = document.createElement("values"); //$NON-NLS-1$
          parameterElement.appendChild(valuesElement);

          final Element valueElement = document.createElement("value"); //$NON-NLS-1$
          valuesElement.appendChild(valueElement);
          valueElement.setAttribute("type", valueType.getName()); //$NON-NLS-1$
          valueElement.setAttribute("selected", "true");//$NON-NLS-1$
          valueElement.setAttribute("null", "false"); //$NON-NLS-1$ //$NON-NLS-2$
          valueElement.setAttribute("value",
              convertParameterValueToString(parameter, parameterContext, selections, valueType)); //$NON-NLS-1$ //$NON-NLS-2$
        }
      }
      return parameterElement;
    }
    catch (BeanException be)
    {
      logger.error(Messages.getString
          ("ReportPlugin.errorFailedToGenerateParameter", parameter.getName(), String.valueOf(selections)), be);
      throw be;
    }
  }

  private String computeTimeZoneHint(final ParameterDefinitionEntry parameter,
                                     final ParameterContext parameterContext)
  {
    // add a timezone hint ..
    final String timezoneSpec = parameter.getParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.TIMEZONE, parameterContext);
    if ("client".equals(timezoneSpec))
    {
      return ("");
    }
    else
    {
      final TimeZone timeZone;
      final StringBuffer value = new StringBuffer();
      if (timezoneSpec == null || "server".equals(timezoneSpec))
      {
        timeZone = TimeZone.getDefault();
      }
      else if ("utc".equals(timezoneSpec))
      {
        timeZone = TimeZone.getTimeZone("UTC");
      }
      else
      {
        timeZone = TimeZone.getTimeZone(timezoneSpec);
      }

      final int rawOffset = timeZone.getRawOffset();
      if (rawOffset < 0)
      {
        value.append("-");
      }
      else
      {
        value.append("+");
      }

      final int seconds = Math.abs(rawOffset / 1000);
      final int minutesRaw = seconds / 60;
      final int hours = minutesRaw / 60;
      final int minutes = minutesRaw % 60;
      if (hours < 10)
      {
        value.append("0");
      }
      value.append(hours);
      if (minutes < 10)
      {
        value.append("0");
      }
      value.append(minutes);
      return value.toString();
    }
  }

  private static String convertParameterValueToString(final ParameterDefinitionEntry parameter,
                                                      final ParameterContext context,
                                                      final Object value,
                                                      final Class type) throws BeanException
  {
    if (value == null)
    {
      return null;
    }

    final ValueConverter valueConverter = ConverterRegistry.getInstance().getValueConverter(type);
    if (valueConverter == null)
    {
      return String.valueOf(value);
    }
    if (Date.class.isAssignableFrom(type))
    {
      if (value instanceof Date == false)
      {
        throw new BeanException(Messages.getString("ReportPlugin.errorNonDateParameterValue"));
      }

      final String timezone = parameter.getParameterAttribute
          (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.TIMEZONE, context);
      final DateFormat dateFormat;
      if (timezone == null ||
          "server".equals(timezone) ||
          "client".equals(timezone))
      {
        // nothing needed ..
        // for server: Just print it as it is, including the server timezone.
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");//$NON-NLS-1$
      }
      else
      {
        // for convinience for the clients we send the date in the correct timezone.
        final TimeZone timeZoneObject;
        if ("utc".equals(timezone))
        {
          timeZoneObject = TimeZone.getTimeZone("UTC");
        }
        else
        {
          timeZoneObject = TimeZone.getTimeZone(timezone);
        }
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");//$NON-NLS-1$
        dateFormat.setTimeZone(timeZoneObject);
      }
      final Date d = (Date) value;
      return dateFormat.format(d);
    }
    if (Number.class.isAssignableFrom(type))
    {
      final ValueConverter numConverter = ConverterRegistry.getInstance().getValueConverter(BigDecimal.class);
      return numConverter.toAttributeValue(new BigDecimal(String.valueOf(value)));
    }
    return valueConverter.toAttributeValue(value);
  }

  private Element createErrorElements(final ValidationResult vr)
  {
    final Element errors = document.createElement("errors"); //$NON-NLS-1$
    for (final String property : vr.getProperties())
    {
      for (final ValidationMessage message : vr.getErrors(property))
      {
        final Element error = document.createElement("error"); //$NON-NLS-1$
        error.setAttribute("parameter", property);//$NON-NLS-1$
        error.setAttribute("message", message.getMessage());//$NON-NLS-1$
        errors.appendChild(error);
      }
    }
    final ValidationMessage[] globalMessages = vr.getErrors();
    for (int i = 0; i < globalMessages.length; i++)
    {
      final ValidationMessage globalMessage = globalMessages[i];
      final Element error = document.createElement("global-error"); //$NON-NLS-1$
      error.setAttribute("message", globalMessage.getMessage());//$NON-NLS-1$
      errors.appendChild(error);
    }
    return errors;
  }

  private static void appendPageCount(final SimpleReportingComponent reportComponent, final Element parameters)
      throws Exception
  {
    if (HtmlTableModule.TABLE_HTML_PAGE_EXPORT_TYPE.equals(reportComponent.getComputedOutputTarget()) == false)
    {
      return;
    }

    reportComponent.setOutputStream(new NullOutputStream());
    // pagination always uses HTML
    reportComponent.setOutputTarget(HtmlTableModule.TABLE_HTML_PAGE_EXPORT_TYPE);

    // so that we don't actually produce anything, we'll accept no pages in this mode
    final int acceptedPage = reportComponent.getAcceptedPage();
    reportComponent.setAcceptedPage(-1);

    // we can ONLY get the # of pages by asking the report to run
    if (reportComponent.validate())
    {
      final int totalPageCount = reportComponent.paginate();
      parameters.setAttribute(SimpleReportingComponent.PAGINATE_OUTPUT, "true"); //$NON-NLS-1$
      parameters.setAttribute("page-count", String.valueOf(totalPageCount)); //$NON-NLS-1$ //$NON-NLS-2$
      // use the saved value (we changed it to -1 for performance)
      parameters.setAttribute(SimpleReportingComponent.ACCEPTED_PAGE, String.valueOf(acceptedPage)); //$NON-NLS-1$
    }
  }


  private void hideSubscriptionParameter(final boolean subscribe,
                                         final Map<String, ParameterDefinitionEntry> parameters)
  {
    final boolean hidden = (subscribe == false);
    final ParameterDefinitionEntry destination = parameters.get(SYS_PARAM_DESTINATION);
    if (destination instanceof AbstractParameter)
    {
      final AbstractParameter parameter = (AbstractParameter) destination;
      parameter.setHidden(hidden || parameter.isHidden());
      if (subscribe == false)
      {
        parameter.setMandatory(false);
      }
    }

    final ParameterDefinitionEntry scheduleId = parameters.get(SYS_PARAM_SCHEDULE_ID);
    if (scheduleId instanceof AbstractParameter)
    {
      final AbstractParameter parameter = (AbstractParameter) scheduleId;
      parameter.setHidden(hidden || parameter.isHidden());
      if (subscribe == false)
      {
        parameter.setMandatory(false);
      }
    }

    final ParameterDefinitionEntry scheduleName = parameters.get(SYS_PARAM_SUBSCRIPTION_NAME);
    if (scheduleName instanceof AbstractParameter)
    {
      final AbstractParameter parameter = (AbstractParameter) scheduleName;
      parameter.setHidden(hidden || parameter.isHidden());
      if (subscribe == false)
      {
        parameter.setMandatory(false);
      }
    }
  }

  private PlainParameter createSubscriptionNameParameter()
  {
    final PlainParameter subscriptionName = new PlainParameter(SYS_PARAM_SUBSCRIPTION_NAME, String.class);
    subscriptionName.setMandatory(true);
    subscriptionName.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.PARAMETER_GROUP, GROUP_SUBSCRIPTION);
    subscriptionName.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.PARAMETER_GROUP_LABEL,
            Messages.getString("ReportPlugin.ReportSchedulingOptions"));
    subscriptionName.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.LABEL,
            Messages.getString("ReportPlugin.ReportName"));
    subscriptionName.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.TYPE,
            ParameterAttributeNames.Core.TYPE_TEXTBOX);
    subscriptionName.setRole(ParameterAttributeNames.Core.ROLE_SCHEDULE_PARAMETER);
    return subscriptionName;
  }

  private PlainParameter createDestinationParameter()
  {
    final PlainParameter destinationParameter = new PlainParameter(SYS_PARAM_DESTINATION, String.class);
    destinationParameter.setMandatory(false);
    destinationParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.PARAMETER_GROUP, GROUP_SUBSCRIPTION);
    destinationParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.PARAMETER_GROUP_LABEL,
            Messages.getString("ReportPlugin.ReportSchedulingOptions"));
    destinationParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.LABEL,
            Messages.getString("ReportPlugin.Destination"));
    destinationParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.TYPE,
            ParameterAttributeNames.Core.TYPE_TEXTBOX);
    destinationParameter.setHidden(isEmailConfigured() == false);
    destinationParameter.setRole(ParameterAttributeNames.Core.ROLE_SCHEDULE_PARAMETER);
    return destinationParameter;
  }

  private PlainParameter createGenericSystemParameter(final String parameterName,
                                                      final boolean deprecated)
  {
    return createGenericSystemParameter(parameterName, deprecated, String.class);
  }

  private PlainParameter createGenericSystemParameter(final String parameterName,
                                                      final boolean deprecated,
                                                      final Class type)
  {
    final PlainParameter destinationParameter = new PlainParameter(parameterName, type);
    destinationParameter.setMandatory(false);
    destinationParameter.setHidden(true);
    destinationParameter.setRole(ParameterAttributeNames.Core.ROLE_SYSTEM_PARAMETER);
    destinationParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.PARAMETER_GROUP, GROUP_SYSTEM);
    destinationParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.PARAMETER_GROUP_LABEL,
            Messages.getString("ReportPlugin.SystemParameters"));
    destinationParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.LABEL, parameterName);
    destinationParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.TYPE,
            ParameterAttributeNames.Core.TYPE_TEXTBOX);
    destinationParameter.setDeprecated(deprecated);
    return destinationParameter;
  }

  private PlainParameter createGenericBooleanSystemParameter(final String parameterName,
                                                             final boolean deprecated)
  {
    return createGenericSystemParameter(parameterName, deprecated, Boolean.class);
  }

  private PlainParameter createGenericIntSystemParameter(final String parameterName,
                                                         final boolean deprecated)
  {
    return createGenericSystemParameter(parameterName, deprecated, Integer.class);
  }

  private StaticListParameter createScheduleIdParameter()
  {

    final StaticListParameter scheduleIdParameter = new StaticListParameter(SYS_PARAM_SCHEDULE_ID, false, true, String.class);
    scheduleIdParameter.setMandatory(true);
    scheduleIdParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.PARAMETER_GROUP, GROUP_SUBSCRIPTION);
    scheduleIdParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.PARAMETER_GROUP_LABEL,
            Messages.getString("ReportPlugin.ReportSchedulingOptions"));
    scheduleIdParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.LABEL,
            Messages.getString("ReportPlugin.Subscription"));
    scheduleIdParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.TYPE,
            ParameterAttributeNames.Core.TYPE_DROPDOWN);
    scheduleIdParameter.setRole(ParameterAttributeNames.Core.ROLE_SCHEDULE_PARAMETER);

    appendAvailableSchedules(scheduleIdParameter);
    return scheduleIdParameter;
  }

  private void appendAvailableSchedules(final StaticListParameter scheduleIdParameter)
  {
    final ISubscriptionRepository subscriptionRepository = PentahoSystem.get(ISubscriptionRepository.class, userSession);
    if (subscriptionRepository == null)
    {
      return;
    }

    final ISubscribeContent subscribeContent = subscriptionRepository.getContentByActionReference(reportDefinitionPath);
    if (subscribeContent == null)
    {
      return;
    }

    final List<ISchedule> list = subscribeContent.getSchedules();
    if (list == null)
    {
      return;
    }

    for (final ISchedule schedule : list)
    {
      scheduleIdParameter.addValues(schedule.getId(), schedule.getTitle());
    }
  }

  private String lookupSchedules()
  {
    final Object scheduleIdSelection = inputs.get(SYS_PARAM_SCHEDULE_ID); //$NON-NLS-1$
    if (scheduleIdSelection != null)
    {
      return String.valueOf(scheduleIdSelection);
    }
    return null;
  }

  private boolean isEmailConfigured()
  {
    final String emailRaw = PentahoSystem.getSystemSetting("smtp-email/email_config.xml", "mail.smtp.host", "");//$NON-NLS-1$
    return StringUtils.isEmpty(emailRaw) == false;
  }

  private Object lookupSubscriptionName()
  {
    final ISubscription subscription = contentGenerator.getSubscription();
    Object reportNameSelection = inputs.get(SYS_PARAM_SUBSCRIPTION_NAME); //$NON-NLS-1$
    if (reportNameSelection == null && subscription != null)
    {
      // subscription helper will populate with this value, grr.
      reportNameSelection = subscription.getTitle();
    }
    return reportNameSelection;
  }

  private Object lookupDestination()
  {
    final ISubscription subscription = contentGenerator.getSubscription();
    Object destinationSelection = inputs.get(SYS_PARAM_DESTINATION);//$NON-NLS-1$
    if (destinationSelection == null && subscription != null)
    {
      destinationSelection = subscription.getTitle();
    }
    return destinationSelection;
  }

  private StaticListParameter createOutputParameter()
  {

    final StaticListParameter listParameter = new StaticListParameter
        (SYS_PARAM_OUTPUT_TARGET, false, true, String.class);
    listParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.PARAMETER_GROUP, GROUP_PARAMETERS);
    listParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.PARAMETER_GROUP_LABEL,
            Messages.getString("ReportPlugin.ReportParameters"));
    listParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.LABEL,
            Messages.getString("ReportPlugin.OutputType"));
    listParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.TYPE,
            ParameterAttributeNames.Core.TYPE_DROPDOWN);
    listParameter.setRole(ParameterAttributeNames.Core.ROLE_SYSTEM_PARAMETER);
    listParameter.addValues(HtmlTableModule.TABLE_HTML_PAGE_EXPORT_TYPE, Messages.getString("ReportPlugin.outputHTMLPaginated"));
    listParameter.addValues(HtmlTableModule.TABLE_HTML_STREAM_EXPORT_TYPE, Messages.getString("ReportPlugin.outputHTMLStream"));
    listParameter.addValues(PdfPageableModule.PDF_EXPORT_TYPE, Messages.getString("ReportPlugin.outputPDF"));
    listParameter.addValues(ExcelTableModule.EXCEL_FLOW_EXPORT_TYPE, Messages.getString("ReportPlugin.outputXLS"));
    listParameter.addValues(ExcelTableModule.XLSX_FLOW_EXPORT_TYPE, Messages.getString("ReportPlugin.outputXLSX"));
    listParameter.addValues(CSVTableModule.TABLE_CSV_STREAM_EXPORT_TYPE, Messages.getString("ReportPlugin.outputCSV"));
    listParameter.addValues(RTFTableModule.TABLE_RTF_FLOW_EXPORT_TYPE, Messages.getString("ReportPlugin.outputRTF"));
    listParameter.addValues(PlainTextPageableModule.PLAINTEXT_EXPORT_TYPE, Messages.getString("ReportPlugin.outputTXT"));
    return listParameter;
  }

  private ParameterDefinitionEntry createRenderModeSystemParameter()
  {
    final StaticListParameter listParameter = new StaticListParameter
        (SYS_PARAM_RENDER_MODE, false, true, String.class);
    listParameter.setHidden(true);
    listParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.PARAMETER_GROUP, GROUP_SYSTEM);
    listParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.PARAMETER_GROUP_LABEL,
            Messages.getString("ReportPlugin.SystemParameters"));
    listParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.LABEL, SYS_PARAM_RENDER_MODE);
    listParameter.setParameterAttribute
        (ParameterAttributeNames.Core.NAMESPACE, ParameterAttributeNames.Core.TYPE,
            ParameterAttributeNames.Core.TYPE_DROPDOWN);
    listParameter.setRole(ParameterAttributeNames.Core.ROLE_SYSTEM_PARAMETER);
    listParameter.addValues("XML", "XML"); // NON-NLS
    listParameter.addValues("REPORT", "REPORT"); // NON-NLS
    listParameter.addValues("SUBSCRIBE", "SUBSCRIBE"); // NON-NLS
    listParameter.addValues("DOWNLOAD", "DOWNLOAD"); // NON-NLS
    return listParameter;
  }


  private Boolean requestFlag(final String parameter,
                              final MasterReport report,
                              final String attributeNamespace, final String attributeName,
                              final String configurationKey)
  {
    if (parameter != null)
    {
      final IParameterProvider parameters = getRequestParameters();
      final String parameterValue = parameters.getStringParameter(parameter, "");
      if ("true".equals(parameterValue))
      {
        return Boolean.TRUE;
      }
      if ("false".equals(parameterValue))
      {
        return Boolean.FALSE;
      }
    }

    if (attributeNamespace != null && attributeName != null)
    {
      final Object attr = report.getAttribute(attributeNamespace, attributeName);
      if (Boolean.TRUE.equals(attr) || "true".equals(attr))
      {
        return Boolean.TRUE;
      }
      if (Boolean.FALSE.equals(attr) || "false".equals(attr))
      {
        return Boolean.FALSE;
      }
    }

    if (configurationKey != null)
    {
      final String configProperty = report.getConfiguration().getConfigProperty(configurationKey);
      if ("true".equals(configProperty))
      {
        return Boolean.TRUE;
      }
      if ("false".equals(configProperty))
      {
        return Boolean.FALSE;
      }
    }
    return null;
  }


  private String requestConfiguration(final String parameter,
                                      final MasterReport report,
                                      final String attributeNamespace, final String attributeName,
                                      final String configurationKey)
  {
    if (parameter != null)
    {
      final IParameterProvider parameters = getRequestParameters();
      final String parameterValue = parameters.getStringParameter(parameter, "");
      if (StringUtils.isEmpty(parameterValue) == false)
      {
        return parameterValue;
      }
    }

    if (attributeNamespace != null && attributeName != null)
    {
      final Object attr = report.getAttribute(attributeNamespace, attributeName);
      if (attr != null && StringUtils.isEmpty(String.valueOf(attr)) == false)
      {
        return String.valueOf(attr);
      }
    }

    if (configurationKey != null)
    {
      final String configProperty = report.getConfiguration().getConfigProperty(configurationKey);
      if (StringUtils.isEmpty(configProperty) == false)
      {
        return configProperty;
      }
    }
    return null;
  }
}