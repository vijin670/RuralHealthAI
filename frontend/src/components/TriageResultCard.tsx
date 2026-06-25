import { motion } from 'framer-motion';
import type { TriageResponse } from '../types';
import { getTranslations } from '../i18n';

// Clean Custom Inline SVGs to avoid any layout squashing or external library loading issues
const CheckCircleIcon = ({ className = "w-6 h-6" }: { className?: string }) => (
  <svg className={className} fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" viewBox="0 0 24 24">
    <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14" />
    <polyline points="22 4 12 14.01 9 11.01" />
  </svg>
);

const HeartPulseIcon = ({ className = "w-6 h-6" }: { className?: string }) => (
  <svg className={className} fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" viewBox="0 0 24 24">
    <path d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3 .5-4.5 2-1.5-1.5-2.74-2-4.5-2A5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z" />
    <path d="M3.22 12H9.5l1.5-3.5L13.5 16l1.5-5.5L16.5 13h4.28" />
  </svg>
);

const HospitalIcon = ({ className = "w-6 h-6" }: { className?: string }) => (
  <svg className={className} fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" viewBox="0 0 24 24">
    <path d="M18 22V8a2 2 0 0 0-2-2H8a2 2 0 0 0-2 2v14" />
    <path d="M4 22h16" />
    <path d="M10 14h4" />
    <path d="M12 12v4" />
    <path d="M12 2v4" />
    <path d="M10 4h4" />
  </svg>
);

const AlertTriangleIcon = ({ className = "w-6 h-6" }: { className?: string }) => (
  <svg className={className} fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" viewBox="0 0 24 24">
    <path d="m21.73 18-8-14a2 2 0 0 0-3.48 0l-8 14A2 2 0 0 0 4 21h16a2 2 0 0 0 1.73-3Z" />
    <line x1="12" y1="9" x2="12" y2="13" />
    <line x1="12" y1="17" x2="12.01" y2="17" />
  </svg>
);

const HelpCircleIcon = ({ className = "w-6 h-6" }: { className?: string }) => (
  <svg className={className} fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" viewBox="0 0 24 24">
    <circle cx="12" cy="12" r="10" />
    <path d="M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3" />
    <line x1="12" y1="17" x2="12.01" y2="17" />
  </svg>
);

const ActivitySquareIcon = ({ className = "w-6 h-6" }: { className?: string }) => (
  <svg className={className} fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" viewBox="0 0 24 24">
    <rect width="18" height="18" x="3" y="3" rx="2" />
    <path d="M17 12h-3l-2.5 6L8.5 6 6 12H3" />
  </svg>
);

const SparklesIcon = ({ className = "w-6 h-6" }: { className?: string }) => (
  <svg className={className} fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" viewBox="0 0 24 24">
    <path d="m12 3-1.912 5.813a2 2 0 0 1-1.275 1.275L3 12l5.813 1.912a2 2 0 0 1 1.275 1.275L12 21l1.912-5.813a2 2 0 0 1 1.275-1.275L21 12l-5.813-1.912a2 2 0 0 1-1.275-1.275L12 3Z" />
    <path d="m5 3 1 2.5L8.5 6 6 7 5 9.5 4 7 1.5 6 4 5 5 3Z" fill="currentColor" fillOpacity="0.2" />
    <path d="m19 17 1 2.5 2.5.5-2.5 1-1 2.5-1-2.5-2.5-1 2.5-1 1-2.5Z" fill="currentColor" fillOpacity="0.2" />
  </svg>
);

const ClipboardListIcon = ({ className = "w-6 h-6" }: { className?: string }) => (
  <svg className={className} fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" viewBox="0 0 24 24">
    <rect width="8" height="4" x="8" y="2" rx="1" />
    <path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2" />
    <path d="M9 12h6" />
    <path d="M9 16h6" />
    <path d="M9 8h6" />
  </svg>
);

interface TriageResultCardProps {
  result: TriageResponse;
  selectedLanguage: string;
}

const getStyles = (level: string, t: any) => {
  switch (level.toLowerCase()) {
    case 'self-care': return { 
      color: 'text-triage-selfCare', 
      bgGradient: 'bg-gradient-to-r from-triage-selfCareBg/40 to-background-card', 
      border: 'border-triage-selfCare/30',
      icon: <CheckCircleIcon className="text-triage-selfCare shrink-0 w-7 h-7" />,
      label: t.selfCareLabel,
      desc: t.selfCareDesc 
    };
    case 'clinic': return { 
      color: 'text-triage-clinic', 
      bgGradient: 'bg-gradient-to-r from-triage-clinicBg/40 to-background-card', 
      border: 'border-triage-clinic/30',
      icon: <HeartPulseIcon className="text-triage-clinic shrink-0 w-7 h-7" />,
      label: t.clinicLabel,
      desc: t.clinicDesc 
    };
    case 'hospital': return { 
      color: 'text-triage-hospital', 
      bgGradient: 'bg-gradient-to-r from-triage-hospitalBg/40 to-background-card', 
      border: 'border-triage-hospital/30',
      icon: <HospitalIcon className="text-triage-hospital shrink-0 w-7 h-7" />,
      label: t.hospitalLabel,
      desc: t.hospitalDesc 
    };
    case 'emergency': return { 
      color: 'text-triage-emergency', 
      bgGradient: 'bg-gradient-to-r from-triage-emergencyBg/40 to-background-card animate-pulse-glow', 
      border: 'border-triage-emergency shadow-[0_0_20px_rgba(244,67,54,0.15)]',
      icon: <AlertTriangleIcon className="text-triage-emergency shrink-0 w-7 h-7" />,
      label: t.emergencyLabel,
      desc: t.emergencyDesc 
    };
    default: return { 
      color: 'text-triage-clinic', 
      bgGradient: 'bg-gradient-to-r from-triage-clinicBg/40 to-background-card', 
      border: 'border-triage-clinic/30',
      icon: <HelpCircleIcon className="text-triage-clinic shrink-0 w-7 h-7" />,
      label: 'UNKNOWN',
      desc: 'Please consult a professional' 
    };
  }
};

export function TriageResultCard({ result, selectedLanguage }: TriageResultCardProps) {
  const t = getTranslations(selectedLanguage);
  const styles = getStyles(result.triage_level, t);

  return (
    <motion.div 
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      className={`rounded-2xl border-2 ${styles.border} bg-background-card overflow-hidden shadow-2xl`}
    >
      {/* Banner */}
      <div className={`p-6 ${styles.bgGradient} flex flex-col sm:flex-row items-start sm:items-center gap-4 border-b border-background-variant/40`}>
        <div className={`p-3 rounded-xl bg-background-dark/80 border border-background-variant shadow-inner shrink-0`}>
          {styles.icon}
        </div>
        <div>
          <h2 className={`text-2xl font-black tracking-wide ${styles.color}`}>{styles.label}</h2>
          <p className="text-text-secondary text-sm mt-0.5">{styles.desc}</p>
        </div>
      </div>

      <div className="p-6 space-y-8">
        {/* Follow up question */}
        {result.follow_up_question && (
          <div className="p-4 rounded-xl bg-accent-blue/10 border border-accent-blue/20 flex gap-4">
            <HelpCircleIcon className="text-accent-blue shrink-0 mt-0.5 w-5 h-5" />
            <div>
              <p className="text-xs font-bold text-accent-blue uppercase tracking-wider mb-1">{t.followUpQuestion}</p>
              <p className="text-text-primary text-sm leading-relaxed">{result.follow_up_question}</p>
            </div>
          </div>
        )}

        {/* AI Summary - Compassionate Medical Explanation */}
        {result.summary && (
          <div className="p-5 rounded-xl bg-primary-DEFAULT/5 border border-primary-DEFAULT/20 flex gap-4 animate-pulse-glow" style={{ animationDuration: '4s' }}>
            <SparklesIcon className="text-primary-DEFAULT shrink-0 mt-1 w-[22px] h-[22px]" />
            <div>
              <h3 className="text-lg font-bold text-primary-light mb-2">{t.aiSummary}</h3>
              <p className="text-text-primary text-sm leading-relaxed font-normal">{result.summary}</p>
            </div>
          </div>
        )}

        {/* Recommended Next Steps */}
        {result.next_steps && result.next_steps.length > 0 && (
          <div>
            <div className="flex items-center gap-2 mb-4">
              <ClipboardListIcon className="text-primary-DEFAULT shrink-0 w-[22px] h-[22px]" />
              <h3 className="text-lg font-bold text-text-primary">{t.nextSteps}</h3>
            </div>
            <ul className="grid grid-cols-1 gap-3">
              {result.next_steps.map((step, i) => (
                <li key={i} className="flex gap-3 bg-background-variant/40 p-4 rounded-xl items-start border border-background-variant/40">
                  <span className="shrink-0 flex items-center justify-center w-6 h-6 rounded-lg bg-primary-DEFAULT/20 text-primary-light text-xs font-bold mt-0.5">
                    {i + 1}
                  </span>
                  <span className="text-text-primary text-sm leading-relaxed">{step}</span>
                </li>
              ))}
            </ul>
          </div>
        )}

        {/* Probable Conditions */}
        {result.conditions.length > 0 && (
          <div>
            <div className="flex items-center gap-2 mb-4">
              <ActivitySquareIcon className="text-primary-DEFAULT shrink-0 w-[22px] h-[22px]" />
              <h3 className="text-lg font-bold text-text-primary">{t.probableConditions}</h3>
            </div>
            
            <div className="space-y-4">
              {result.conditions.map((cond, i) => {
                const confPercent = Math.round(cond.confidence * 100);
                const barColor = confPercent >= 70 ? 'bg-triage-selfCare' : confPercent >= 40 ? 'bg-triage-clinic' : 'bg-triage-hospital';
                const textColor = confPercent >= 70 ? 'text-triage-selfCare' : confPercent >= 40 ? 'text-triage-clinic' : 'text-triage-hospital';
                
                return (
                  <motion.div 
                    initial={{ opacity: 0, x: -20 }}
                    animate={{ opacity: 1, x: 0 }}
                    transition={{ delay: i * 0.15 }}
                    key={i} 
                    className="p-4 rounded-xl bg-background-variant/20 border border-background-variant/40"
                  >
                    <div className="flex justify-between items-center mb-2">
                      <span className="font-semibold text-text-primary">{cond.condition}</span>
                      <span className={`font-bold ${textColor}`}>{confPercent}%</span>
                    </div>
                    {/* Confidence bar */}
                    <div className="w-full h-1.5 bg-background-variant rounded-full mb-2 overflow-hidden">
                      <div className={`h-full ${barColor} rounded-full`} style={{ width: `${confPercent}%` }} />
                    </div>
                    <p className="text-xs text-text-tertiary leading-relaxed">{cond.description}</p>
                  </motion.div>
                );
              })}
            </div>
          </div>
        )}

        {/* First Aid */}
        {result.first_aid.length > 0 && (
          <div>
            <div className="flex items-center gap-2 mb-4">
              <HeartPulseIcon className="text-primary-DEFAULT shrink-0 w-[22px] h-[22px]" />
              <h3 className="text-lg font-bold text-text-primary">{t.firstAidSteps}</h3>
            </div>
            <ul className="space-y-3">
              {result.first_aid.map((step, i) => (
                <li key={i} className="flex gap-3 bg-secondary-container/10 p-4 rounded-xl items-start border border-secondary-container/20">
                  <span className="shrink-0 flex items-center justify-center w-6 h-6 rounded-lg bg-secondary-DEFAULT/20 text-secondary-light text-xs font-bold mt-0.5">
                    {i + 1}
                  </span>
                  <span className="text-text-primary text-sm leading-relaxed">{step}</span>
                </li>
              ))}
            </ul>
          </div>
        )}

        {/* Disclaimer */}
        <p className="text-center text-xs text-text-tertiary bg-background-variant/10 p-4 rounded-xl border border-background-variant/30 leading-relaxed">
          ⚕ {result.disclaimer}
        </p>
      </div>
    </motion.div>
  );
}
