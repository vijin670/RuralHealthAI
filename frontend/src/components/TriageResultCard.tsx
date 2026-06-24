import { motion } from 'framer-motion';
import { CheckCircle, HeartPulse, Hospital, AlertTriangle, HelpCircle, ActivitySquare, Sparkles, ClipboardList } from 'lucide-react';
import type { TriageResponse } from '../types';
import { getTranslations } from '../i18n';

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
      icon: <CheckCircle className="text-triage-selfCare" size={28} />,
      label: t.selfCareLabel,
      desc: t.selfCareDesc 
    };
    case 'clinic': return { 
      color: 'text-triage-clinic', 
      bgGradient: 'bg-gradient-to-r from-triage-clinicBg/40 to-background-card', 
      border: 'border-triage-clinic/30',
      icon: <HeartPulse className="text-triage-clinic" size={28} />,
      label: t.clinicLabel,
      desc: t.clinicDesc 
    };
    case 'hospital': return { 
      color: 'text-triage-hospital', 
      bgGradient: 'bg-gradient-to-r from-triage-hospitalBg/40 to-background-card', 
      border: 'border-triage-hospital/30',
      icon: <Hospital className="text-triage-hospital" size={28} />,
      label: t.hospitalLabel,
      desc: t.hospitalDesc 
    };
    case 'emergency': return { 
      color: 'text-triage-emergency', 
      bgGradient: 'bg-gradient-to-r from-triage-emergencyBg/40 to-background-card animate-pulse-glow', 
      border: 'border-triage-emergency shadow-[0_0_20px_rgba(244,67,54,0.15)]',
      icon: <AlertTriangle className="text-triage-emergency" size={28} />,
      label: t.emergencyLabel,
      desc: t.emergencyDesc 
    };
    default: return { 
      color: 'text-triage-clinic', 
      bgGradient: 'bg-gradient-to-r from-triage-clinicBg/40 to-background-card', 
      border: 'border-triage-clinic/30',
      icon: <HelpCircle className="text-triage-clinic" size={28} />,
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
      <div className={`p-6 ${styles.bgGradient} flex items-center gap-4 border-b border-background-variant/40`}>
        <div className={`p-3 rounded-xl bg-background-dark/80 border border-background-variant shadow-inner`}>
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
            <HelpCircle className="text-accent-blue shrink-0 mt-0.5" size={20} />
            <div>
              <p className="text-xs font-bold text-accent-blue uppercase tracking-wider mb-1">{t.followUpQuestion}</p>
              <p className="text-text-primary text-sm leading-relaxed">{result.follow_up_question}</p>
            </div>
          </div>
        )}

        {/* AI Summary - Compassionate Medical Explanation */}
        {result.summary && (
          <div className="p-5 rounded-xl bg-primary-DEFAULT/5 border border-primary-DEFAULT/20 flex gap-4 animate-pulse-glow" style={{ animationDuration: '4s' }}>
            <Sparkles className="text-primary-DEFAULT shrink-0 mt-1" size={22} />
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
              <ClipboardList className="text-primary-DEFAULT" size={22} />
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
              <ActivitySquare className="text-primary-DEFAULT" size={22} />
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
              <HeartPulse className="text-primary-DEFAULT" size={22} />
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
