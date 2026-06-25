import type { HospitalInfo } from '../types';
import { motion } from 'framer-motion';
import { getTranslations } from '../i18n';

// Clean Custom Inline SVGs to avoid any layout squashing or external library loading issues
const MapPinIcon = ({ className = "w-5 h-5" }: { className?: string }) => (
  <svg className={className} fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" viewBox="0 0 24 24">
    <path d="M20 10c0 6-8 12-8 12s-8-6-8-12a8 8 0 0 1 16 0Z" />
    <circle cx="12" cy="10" r="3" />
  </svg>
);

const PhoneIcon = ({ className = "w-5 h-5" }: { className?: string }) => (
  <svg className={className} fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" viewBox="0 0 24 24">
    <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z" />
  </svg>
);

const NavigationIcon = ({ className = "w-5 h-5" }: { className?: string }) => (
  <svg className={className} fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" viewBox="0 0 24 24">
    <polygon points="3 11 22 2 13 21 11 13 3 11" />
  </svg>
);

interface HospitalListCardProps {
  hospitals: HospitalInfo[];
  isLoading: boolean;
  selectedLanguage: string;
}

export function HospitalListCard({ hospitals, isLoading, selectedLanguage }: HospitalListCardProps) {
  const t = getTranslations(selectedLanguage);

  if (isLoading) {
    return (
      <div className="glass-card rounded-2xl p-8 flex flex-col items-center justify-center text-center">
        <div className="w-10 h-10 border-4 border-primary-DEFAULT border-t-transparent rounded-full animate-spin mb-4" />
        <p className="text-text-secondary">{t.searchingHospitals}</p>
      </div>
    );
  }

  if (hospitals.length === 0) {
    return (
      <div className="glass-card rounded-2xl p-8 flex flex-col items-center justify-center text-center">
        <MapPinIcon className="text-text-tertiary mb-3 w-8 h-8 shrink-0" />
        <p className="text-text-secondary">{t.noHospitalsFound}</p>
      </div>
    );
  }

  return (
    <div className="glass-card rounded-2xl overflow-hidden">
      <div className="p-6 bg-background-variant/30 border-b border-background-variant">
        <h2 className="text-xl font-bold flex items-center gap-2">
          <MapPinIcon className="text-primary-DEFAULT shrink-0" /> {t.nearbyFacilities}
        </h2>
        <p className="text-sm text-text-tertiary mt-1">
          {hospitals.length} {t.locationsFound}
        </p>
      </div>
      
      <div className="divide-y divide-background-variant">
        {hospitals.map((hospital, index) => (
          <motion.div 
            initial={{ opacity: 0, y: 10 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: index * 0.1 }}
            key={index}
            className="p-5 hover:bg-background-variant/20 transition-colors"
          >
            <h3 className="font-semibold text-text-primary mb-2 text-lg">{hospital.name}</h3>
            
            <div className="space-y-2 text-sm">
              <div className="flex items-start gap-2 text-accent-blue">
                <NavigationIcon className="shrink-0 mt-0.5 w-[16px] h-[16px]" />
                <span className="font-medium">{hospital.distance_km} {t.kmAway}</span>
              </div>
              
              {hospital.address && (
                <div className="flex items-start gap-2 text-text-secondary">
                  <MapPinIcon className="shrink-0 mt-0.5 w-[16px] h-[16px]" />
                  <span>{hospital.address}</span>
                </div>
              )}
              
              {hospital.phone && (
                <div className="flex items-start gap-2 text-triage-selfCare">
                  <PhoneIcon className="shrink-0 mt-0.5 w-[16px] h-[16px]" />
                  <span>{hospital.phone}</span>
                </div>
              )}
            </div>
          </motion.div>
        ))}
      </div>
    </div>
  );
}
