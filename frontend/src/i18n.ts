export type LangCode = 'en' | 'hi' | 'ta' | 'te';

export interface Translations {
  appTitle: string;
  appSubtitle: string;
  selectLanguage: string;
  symptomAssessment: string;
  age: string;
  agePlaceholder: string;
  gender: string;
  male: string;
  female: string;
  other: string;
  describeSymptoms: string;
  symptomsPlaceholder: string;
  vitalsLabel: string;
  vitalsPlaceholder: string;
  analyzeButton: string;
  analyzingButton: string;
  newAssessment: string;
  assessmentResults: string;
  probableConditions: string;
  firstAidSteps: string;
  followUpQuestion: string;
  aiSummary: string;
  nextSteps: string;
  selfCareLabel: string;
  selfCareDesc: string;
  clinicLabel: string;
  clinicDesc: string;
  hospitalLabel: string;
  hospitalDesc: string;
  emergencyLabel: string;
  emergencyDesc: string;
  nearbyFacilities: string;
  locationsFound: string;
  kmAway: string;
  searchingHospitals: string;
  noHospitalsFound: string;
  disclaimer: string;
  errorPrefix: string;
  validAge: string;
  describeYourSymptoms: string;
  connectionFailed: string;
  voiceNotSupported: string;
  startVoice: string;
  stopVoice: string;
}

const translations: Record<LangCode, Translations> = {
  en: {
    appTitle: 'Rural Health AI',
    appSubtitle: 'Multilingual Symptom Triage Assistant',
    selectLanguage: 'Select Language',
    symptomAssessment: 'Symptom Assessment',
    age: 'Age',
    agePlaceholder: 'e.g. 35',
    gender: 'Gender',
    male: 'Male',
    female: 'Female',
    other: 'Other',
    describeSymptoms: 'Describe Your Symptoms',
    symptomsPlaceholder: 'e.g. I have a severe headache and fever since 2 days...',
    vitalsLabel: 'Vitals (Optional)',
    vitalsPlaceholder: 'e.g. BP: 120/80, Temp: 101°F',
    analyzeButton: 'Analyze Symptoms',
    analyzingButton: 'Analyzing Symptoms...',
    newAssessment: 'New Assessment',
    assessmentResults: 'Assessment Results',
    probableConditions: 'Probable Conditions',
    firstAidSteps: 'First-Aid Steps',
    followUpQuestion: 'Follow-up Question',
    aiSummary: 'AI Summary',
    nextSteps: 'Recommended Next Steps',
    selfCareLabel: 'SELF-CARE',
    selfCareDesc: 'Manageable at home with basic care',
    clinicLabel: 'CLINIC VISIT',
    clinicDesc: 'Visit a doctor when possible',
    hospitalLabel: 'HOSPITAL',
    hospitalDesc: 'Requires hospital care soon',
    emergencyLabel: '⚠ EMERGENCY',
    emergencyDesc: 'Seek immediate medical help!',
    nearbyFacilities: 'Nearby Facilities',
    locationsFound: 'locations within range',
    kmAway: 'km away',
    searchingHospitals: 'Searching for nearby hospitals...',
    noHospitalsFound: 'No hospitals found nearby or location access denied.',
    disclaimer: 'This app provides AI-generated guidance only. It is NOT a substitute for professional medical advice. Always consult a qualified doctor.',
    errorPrefix: 'Error:',
    validAge: 'Please provide a valid age',
    describeYourSymptoms: 'Please describe your symptoms',
    connectionFailed: 'Connection failed to backend API',
    voiceNotSupported: 'Voice recognition is not supported in this browser. Please use Chrome/Edge.',
    startVoice: 'Start Voice Input',
    stopVoice: 'Stop Listening',
  },
  hi: {
    appTitle: 'ग्रामीण स्वास्थ्य AI',
    appSubtitle: 'बहुभाषी लक्षण जाँच सहायक',
    selectLanguage: 'भाषा चुनें',
    symptomAssessment: 'लक्षण मूल्यांकन',
    age: 'आयु',
    agePlaceholder: 'जैसे 35',
    gender: 'लिंग',
    male: 'पुरुष',
    female: 'महिला',
    other: 'अन्य',
    describeSymptoms: 'अपने लक्षण बताएं',
    symptomsPlaceholder: 'जैसे मुझे 2 दिनों से तेज़ सिरदर्द और बुखार है...',
    vitalsLabel: 'शारीरिक जानकारी (वैकल्पिक)',
    vitalsPlaceholder: 'जैसे रक्तचाप: 120/80, तापमान: 101°F',
    analyzeButton: 'लक्षणों का विश्लेषण करें',
    analyzingButton: 'लक्षणों का विश्लेषण हो रहा है...',
    newAssessment: 'नई जाँच',
    assessmentResults: 'जाँच के परिणाम',
    probableConditions: 'संभावित स्थितियाँ',
    firstAidSteps: 'प्राथमिक उपचार',
    followUpQuestion: 'अतिरिक्त प्रश्न',
    aiSummary: 'AI सारांश',
    nextSteps: 'अगले कदम',
    selfCareLabel: 'स्व-देखभाल',
    selfCareDesc: 'घर पर सामान्य देखभाल से ठीक हो सकता है',
    clinicLabel: 'क्लिनिक जाएँ',
    clinicDesc: 'जब संभव हो डॉक्टर से मिलें',
    hospitalLabel: 'अस्पताल',
    hospitalDesc: 'जल्द अस्पताल में इलाज की आवश्यकता',
    emergencyLabel: '⚠ आपातकाल',
    emergencyDesc: 'तुरंत चिकित्सा सहायता लें!',
    nearbyFacilities: 'निकटतम अस्पताल',
    locationsFound: 'स्थान मिले',
    kmAway: 'किमी दूर',
    searchingHospitals: 'पास के अस्पतालों की खोज हो रही है...',
    noHospitalsFound: 'पास में कोई अस्पताल नहीं मिला या स्थान की अनुमति नहीं दी गई।',
    disclaimer: 'यह ऐप केवल AI-जनित मार्गदर्शन प्रदान करता है। यह पेशेवर चिकित्सा सलाह का विकल्प नहीं है। कृपया हमेशा योग्य डॉक्टर से परामर्श लें।',
    errorPrefix: 'त्रुटि:',
    validAge: 'कृपया एक मान्य आयु दर्ज करें',
    describeYourSymptoms: 'कृपया अपने लक्षण बताएं',
    connectionFailed: 'सर्वर से कनेक्शन विफल',
    voiceNotSupported: 'इस ब्राउज़र में आवाज़ पहचान उपलब्ध नहीं है। कृपया Chrome/Edge का उपयोग करें।',
    startVoice: 'आवाज़ से बोलें',
    stopVoice: 'सुनना बंद करें',
  },
  ta: {
    appTitle: 'கிராமப்புற சுகாதார AI',
    appSubtitle: 'பன்மொழி அறிகுறி பரிசோதனை உதவி',
    selectLanguage: 'மொழியைத் தேர்ந்தெடுக்கவும்',
    symptomAssessment: 'அறிகுறி மதிப்பீடு',
    age: 'வயது',
    agePlaceholder: 'எ.கா. 35',
    gender: 'பாலினம்',
    male: 'ஆண்',
    female: 'பெண்',
    other: 'பிற',
    describeSymptoms: 'உங்கள் அறிகுறிகளை விவரிக்கவும்',
    symptomsPlaceholder: 'எ.கா. 2 நாட்களாக கடுமையான தலைவலி மற்றும் காய்ச்சல் உள்ளது...',
    vitalsLabel: 'உடல் அளவீடுகள் (விரும்பினால்)',
    vitalsPlaceholder: 'எ.கா. இரத்த அழுத்தம்: 120/80, வெப்பநிலை: 101°F',
    analyzeButton: 'அறிகுறிகளை ஆய்வு செய்',
    analyzingButton: 'அறிகுறிகள் ஆய்வு செய்யப்படுகின்றன...',
    newAssessment: 'புதிய மதிப்பீடு',
    assessmentResults: 'மதிப்பீட்டு முடிவுகள்',
    probableConditions: 'சாத்தியமான நிலைகள்',
    firstAidSteps: 'முதலுதவி வழிமுறைகள்',
    followUpQuestion: 'கூடுதல் கேள்வி',
    aiSummary: 'AI சுருக்கம்',
    nextSteps: 'அடுத்த நடவடிக்கைகள்',
    selfCareLabel: 'சுய பராமரிப்பு',
    selfCareDesc: 'வீட்டிலேயே சாதாரண பராமரிப்பில் சரியாகும்',
    clinicLabel: 'மருத்துவரை சந்திக்கவும்',
    clinicDesc: 'முடிந்தபோது மருத்துவரிடம் செல்லுங்கள்',
    hospitalLabel: 'மருத்துவமனை',
    hospitalDesc: 'விரைவில் மருத்துவமனை சிகிச்சை தேவை',
    emergencyLabel: '⚠ அவசரநிலை',
    emergencyDesc: 'உடனடி மருத்துவ உதவி பெறுங்கள்!',
    nearbyFacilities: 'அருகிலுள்ள மருத்துவமனைகள்',
    locationsFound: 'இடங்கள் கண்டறியப்பட்டன',
    kmAway: 'கிமீ தொலைவில்',
    searchingHospitals: 'அருகிலுள்ள மருத்துவமனைகளைத் தேடுகிறது...',
    noHospitalsFound: 'அருகில் மருத்துவமனைகள் கிடைக்கவில்லை அல்லது இருப்பிட அனுமதி மறுக்கப்பட்டது.',
    disclaimer: 'இந்த செயலி AI-உருவாக்கிய வழிகாட்டுதலை மட்டுமே வழங்குகிறது. இது மருத்துவ ஆலோசனைக்கு மாற்றாகாது. எப்போதும் தகுதியான மருத்துவரை அணுகவும்.',
    errorPrefix: 'பிழை:',
    validAge: 'தயவுசெய்து சரியான வயதை உள்ளிடவும்',
    describeYourSymptoms: 'தயவுசெய்து உங்கள் அறிகுறிகளை விவரிக்கவும்',
    connectionFailed: 'சர்வர் இணைப்பு தோல்வி',
    voiceNotSupported: 'இந்த உலாவியில் குரல் அங்கீகாரம் ஆதரிக்கப்படவில்லை. Chrome/Edge பயன்படுத்தவும்.',
    startVoice: 'குரல் உள்ளீடு',
    stopVoice: 'நிறுத்து',
  },
  te: {
    appTitle: 'గ్రామీణ ఆరోగ్యం AI',
    appSubtitle: 'బహుభాషా లక్షణ పరీక్ష సహాయకం',
    selectLanguage: 'భాషను ఎంచుకోండి',
    symptomAssessment: 'లక్షణ అంచనా',
    age: 'వయసు',
    agePlaceholder: 'ఉదా. 35',
    gender: 'లింగం',
    male: 'పురుషుడు',
    female: 'స్త్రీ',
    other: 'ఇతరం',
    describeSymptoms: 'మీ లక్షణాలను వివరించండి',
    symptomsPlaceholder: 'ఉదా. 2 రోజులుగా తీవ్రమైన తలనొప్పి మరియు జ్వరం ఉంది...',
    vitalsLabel: 'శారీరక కొలతలు (ఐచ్ఛికం)',
    vitalsPlaceholder: 'ఉదా. రక్తపోటు: 120/80, ఉష్ణోగ్రత: 101°F',
    analyzeButton: 'లక్షణాలను విశ్లేషించు',
    analyzingButton: 'లక్షణాలు విశ్లేషించబడుతున్నాయి...',
    newAssessment: 'కొత్త పరీక్ష',
    assessmentResults: 'పరీక్ష ఫలితాలు',
    probableConditions: 'సంభావ్య పరిస్థితులు',
    firstAidSteps: 'ప్రథమ చికిత్స',
    followUpQuestion: 'అదనపు ప్రశ్న',
    aiSummary: 'AI సారాంశం',
    nextSteps: 'తదుపరి చర్యలు',
    selfCareLabel: 'స్వీయ సంరక్షణ',
    selfCareDesc: 'ఇంట్లోనే సాధారణ సంరక్షణతో నయమవుతుంది',
    clinicLabel: 'క్లినిక్‌కు వెళ్ళండి',
    clinicDesc: 'వీలైనప్పుడు వైద్యుడిని సంప్రదించండి',
    hospitalLabel: 'ఆసుపత్రి',
    hospitalDesc: 'త్వరలో ఆసుపత్రి చికిత్స అవసరం',
    emergencyLabel: '⚠ అత్యవసరం',
    emergencyDesc: 'వెంటనే వైద్య సహాయం పొందండి!',
    nearbyFacilities: 'సమీపంలోని ఆసుపత్రులు',
    locationsFound: 'ప్రదేశాలు కనుగొనబడ్డాయి',
    kmAway: 'కి.మీ. దూరంలో',
    searchingHospitals: 'సమీపంలోని ఆసుపత్రులను వెతుకుతోంది...',
    noHospitalsFound: 'సమీపంలో ఆసుపత్రులు కనుగొనబడలేదు లేదా స్థాన అనుమతి నిరాకరించబడింది.',
    disclaimer: 'ఈ యాప్ AI-ఉత్పత్తి మార్గదర్శకత్వాన్ని మాత్రమే అందిస్తుంది. ఇది వైద్య సలహాకు ప్రత్యామ్నాయం కాదు. ఎల్లప్పుడూ అర్హత కలిగిన వైద్యుడిని సంప్రదించండి.',
    errorPrefix: 'లోపం:',
    validAge: 'దయచేసి సరైన వయసును నమోదు చేయండి',
    describeYourSymptoms: 'దయచేసి మీ లక్షణాలను వివరించండి',
    connectionFailed: 'సర్వర్ కనెక్షన్ విఫలమైంది',
    voiceNotSupported: 'ఈ బ్రౌజర్‌లో వాయిస్ గుర్తింపు మద్దతు లేదు. Chrome/Edge ఉపయోగించండి.',
    startVoice: 'వాయిస్ ఇన్‌పుట్',
    stopVoice: 'ఆపు',
  },
};

export function getTranslations(lang: string): Translations {
  return translations[lang as LangCode] || translations.en;
}
