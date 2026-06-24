interface LanguageOption {
  code: string;
  displayName: string;
  nativeName: string;
  flag: string;
}

const supportedLanguages: LanguageOption[] = [
  { code: 'en', displayName: 'English', nativeName: 'English', flag: '🇬🇧' },
  { code: 'hi', displayName: 'Hindi', nativeName: 'हिन्दी', flag: '🇮🇳' },
  { code: 'ta', displayName: 'Tamil', nativeName: 'தமிழ்', flag: '🇮🇳' },
  { code: 'te', displayName: 'Telugu', nativeName: 'తెలుగు', flag: '🇮🇳' }
];

interface LanguageSelectorProps {
  selectedLanguage: string;
  onLanguageSelected: (langCode: string) => void;
}

export function LanguageSelector({ selectedLanguage, onLanguageSelected }: LanguageSelectorProps) {
  return (
    <div className="flex flex-wrap gap-2.5">
      {supportedLanguages.map((lang) => {
        const isSelected = selectedLanguage === lang.code;
        return (
          <button
            key={lang.code}
            onClick={(e) => {
              e.preventDefault();
              onLanguageSelected(lang.code);
            }}
            className={`px-4 py-2.5 rounded-xl text-sm font-medium transition-all duration-300 border flex items-center gap-2 ${
              isSelected 
                ? 'bg-primary-DEFAULT/20 border-primary-DEFAULT text-primary-light shadow-[0_0_15px_rgba(20,163,168,0.15)] font-semibold' 
                : 'bg-background-variant/40 border-background-variant/60 text-text-secondary hover:text-text-primary hover:border-text-secondary hover:bg-background-variant/80'
            }`}
          >
            <span className="text-base">{lang.flag}</span>
            <span>{lang.nativeName}</span>
            <span className="text-xs opacity-60">({lang.displayName})</span>
          </button>
        );
      })}
    </div>
  );
}
