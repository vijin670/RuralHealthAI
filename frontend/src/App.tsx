import { useState } from 'react';
import axios from 'axios';
import { RefreshCcw, Stethoscope } from 'lucide-react';
import type { TriageUiState, TriageResponse, HospitalInfo } from './types';
import { InputForm } from './components/InputForm';
import { TriageResultCard } from './components/TriageResultCard';
import { HospitalListCard } from './components/HospitalListCard';
import { FloatingParticles } from './components/FloatingParticles';
import { getTranslations } from './i18n';

const API_URL = import.meta.env.VITE_API_URL || '/api';

const initialState: TriageUiState = {
  age: '',
  gender: 'Male',
  symptoms: '',
  vitals: '',
  selectedLanguage: 'en',
  isLoading: false,
  isListening: false,
  isLoadingHospitals: false,
  triageResult: null,
  hospitals: [],
  errorMessage: null,
  showResults: false,
};

function App() {
  const [state, setState] = useState<TriageUiState>(initialState);
  const t = getTranslations(state.selectedLanguage);

  const updateState = (updates: Partial<TriageUiState>) => {
    setState(prev => ({ ...prev, ...updates }));
  };

  const handleTriageSubmit = async () => {
    if (!state.age || isNaN(Number(state.age))) {
      alert(t.validAge);
      return;
    }
    if (!state.symptoms) {
      alert(t.describeYourSymptoms);
      return;
    }

    updateState({ isLoading: true, errorMessage: null, showResults: false });

    // 1. Fetch Triage
    try {
      const response = await axios.post<TriageResponse>(`${API_URL}/triage`, {
        age: parseInt(state.age),
        gender: state.gender,
        symptoms: state.symptoms,
        vitals: state.vitals,
        language: state.selectedLanguage
      });
      
      updateState({ 
        isLoading: false, 
        triageResult: response.data, 
        showResults: true 
      });

      // 2. Queue Hospital Fetching via Geolocation API
      fetchHospitals();

    } catch (err: any) {
      console.error(err);
      updateState({ 
        isLoading: false, 
        errorMessage: err?.response?.data?.detail || t.connectionFailed 
      });
    }
  };

  const fetchHospitals = () => {
    if ('geolocation' in navigator) {
      updateState({ isLoadingHospitals: true });
      navigator.geolocation.getCurrentPosition(
        async (position) => {
          try {
            const resp = await axios.post<{hospitals: HospitalInfo[], count: number}>(`${API_URL}/hospitals`, {
              latitude: position.coords.latitude,
              longitude: position.coords.longitude
            });
            updateState({ hospitals: resp.data.hospitals, isLoadingHospitals: false });
          } catch (e) {
            updateState({ isLoadingHospitals: false });
          }
        },
        () => {
          updateState({ isLoadingHospitals: false });
          console.warn("Geolocation denied");
        }
      );
    }
  };

  const resetForm = () => {
    setState({
      ...initialState,
      selectedLanguage: state.selectedLanguage // retain language choice
    });
  };

  return (
    <div className="gradient-bg py-8 px-4 sm:px-6 flex flex-col items-center relative min-h-screen">
      {/* Background Animated Particles */}
      <FloatingParticles />
      
      {/* Main Content (relative z-10 for overlapping floaters) */}
      <div className="w-full max-w-4xl flex items-center justify-between mb-8 relative z-10">
        <div className="flex items-center gap-4">
          <div className="p-3 bg-gradient-to-br from-primary-DEFAULT to-secondary-DEFAULT rounded-2xl shadow-lg animate-pulse-glow" style={{ animationDuration: '6s' }}>
            <Stethoscope className="text-white animate-spin-slow" size={32} />
          </div>
          <div>
            <h1 className="text-3xl font-black tracking-tight text-white">{t.appTitle}</h1>
            <p className="text-primary-light text-sm font-semibold tracking-wide mt-0.5">{t.appSubtitle}</p>
          </div>
        </div>
      </div>

      <div className="w-full max-w-4xl space-y-8 relative z-10">
        
        {/* Error Banner */}
        {state.errorMessage && (
          <div className="bg-accent-red/10 border border-accent-red/30 text-accent-red px-5 py-4 rounded-xl flex items-start gap-3 shadow-lg">
            <span className="font-bold">{t.errorPrefix}</span> {state.errorMessage}
          </div>
        )}

        {/* Input Form */}
        <div className="glass-card rounded-3xl p-6 sm:p-8 relative overflow-hidden">
          {/* Subtle inner top glow for card */}
          <div className="absolute top-0 inset-x-0 h-[1px] bg-gradient-to-r from-transparent via-primary-light/25 to-transparent pointer-events-none" />

          <div className="flex justify-between items-center mb-6">
            <h2 className="text-xl font-bold text-white">{t.symptomAssessment}</h2>
            {state.showResults && (
              <button 
                onClick={resetForm}
                className="flex items-center gap-2 text-sm font-semibold text-primary-light hover:text-white transition-all bg-primary-DEFAULT/10 hover:bg-primary-DEFAULT/25 px-4 py-2 rounded-xl border border-primary-DEFAULT/20 hover:border-primary-DEFAULT/50"
              >
                <RefreshCcw size={15} /> {t.newAssessment}
              </button>
            )}
          </div>
          
          <InputForm state={state} updateState={updateState} />

          <button 
            onClick={handleTriageSubmit}
            disabled={state.isLoading}
            className="w-full mt-8 bg-gradient-to-r from-primary-dark to-primary-DEFAULT hover:from-primary-DEFAULT hover:to-primary-light disabled:from-background-variant disabled:to-background-variant disabled:text-text-tertiary text-white font-bold py-4.5 px-6 rounded-xl shadow-[0_4px_20px_rgba(20,163,168,0.2)] hover:shadow-[0_4px_25px_rgba(20,163,168,0.45)] transition-all duration-300 active:scale-[0.99] flex justify-center items-center gap-3 disabled:cursor-not-allowed border border-primary-DEFAULT/30"
          >
            {state.isLoading ? (
              <>
                 <div className="w-5 h-5 border-2 border-white border-t-transparent rounded-full animate-spin" />
                 {t.analyzingButton}
              </>
            ) : t.analyzeButton}
          </button>
        </div>

        {/* Results Stream */}
        {state.showResults && state.triageResult && (
          <div className="space-y-6">
            <h2 className="text-2xl font-bold text-white border-l-4 border-primary-DEFAULT pl-3">{t.assessmentResults}</h2>
            <TriageResultCard result={state.triageResult} selectedLanguage={state.selectedLanguage} />
            <HospitalListCard hospitals={state.hospitals} isLoading={state.isLoadingHospitals} selectedLanguage={state.selectedLanguage} />
          </div>
        )}

      </div>
      
      {/* Universal Disclaimer */}
      <div className="w-full max-w-4xl mt-12 text-center text-triage-clinic/90 text-sm font-medium bg-triage-clinicBg/30 p-4.5 rounded-xl border border-triage-clinic/20 relative z-10 leading-relaxed">
        ⚕ {t.disclaimer}
      </div>
      
    </div>
  );
}

export default App;
