import React, { useState, useRef, useEffect } from 'react';

// Clean Custom Inline SVGs to avoid any layout squashing or external library loading issues
const MicIcon = ({ className = "w-6 h-6" }: { className?: string }) => (
  <svg className={className} fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" viewBox="0 0 24 24">
    <path d="M12 2a3 3 0 0 0-3 3v7a3 3 0 0 0 6 0V5a3 3 0 0 0-3-3Z" />
    <path d="M19 10v2a7 7 0 0 1-14 0v-2" />
    <line x1="12" y1="19" x2="12" y2="22" />
  </svg>
);

const MicOffIcon = ({ className = "w-6 h-6" }: { className?: string }) => (
  <svg className={className} fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" viewBox="0 0 24 24">
    <line x1="2" y1="2" x2="22" y2="22" />
    <path d="M18.89 13.23A7.12 7.12 0 0 0 19 12v-2" />
    <path d="M5 10v1.5a7 7 0 0 0 10.74 5.92" />
    <path d="M10.12 5.12A3 3 0 0 1 15 7v3.88" />
    <line x1="12" y1="19" x2="12" y2="22" />
  </svg>
);

interface VoiceInputButtonProps {
  onTranscription: (text: string) => void;
  languageCode: string;
}

export function VoiceInputButton({ onTranscription, languageCode }: VoiceInputButtonProps) {
  const [isListening, setIsListening] = useState(false);
  const recognitionRef = useRef<any>(null);

  // Map our UI lang code to BCP 47
  const langMap: Record<string, string> = {
    'en': 'en-US',
    'hi': 'hi-IN',
    'ta': 'ta-IN',
    'te': 'te-IN'
  };

  useEffect(() => {
    // Setup Web Speech API native browser capabilities
    if (typeof window !== 'undefined' && ('SpeechRecognition' in window || 'webkitSpeechRecognition' in window)) {
      const SpeechRecognition = (window as any).SpeechRecognition || (window as any).webkitSpeechRecognition;
      recognitionRef.current = new SpeechRecognition();
      recognitionRef.current.continuous = false;
      recognitionRef.current.interimResults = false;
    }

    return () => {
      if (recognitionRef.current) {
        recognitionRef.current.stop();
      }
    };
  }, []);

  const toggleListen = (e: React.MouseEvent) => {
    e.preventDefault();
    if (!recognitionRef.current) {
      alert("Voice recognition is not supported in this browser. Please use Chrome/Edge.");
      return;
    }

    if (isListening) {
      recognitionRef.current.stop();
      setIsListening(false);
    } else {
      try {
        recognitionRef.current.lang = langMap[languageCode] || 'en-US';
        recognitionRef.current.start();
        setIsListening(true);

        recognitionRef.current.onresult = (event: any) => {
          const transcript = event.results[0][0].transcript;
          onTranscription(transcript);
          setIsListening(false);
        };

        recognitionRef.current.onerror = (event: any) => {
          console.error("Speech Recognition Error", event.error);
          setIsListening(false);
        };

        recognitionRef.current.onend = () => {
          setIsListening(false);
        };
      } catch (err) {
        setIsListening(false);
      }
    }
  };

  return (
    <div className="relative inline-flex items-center justify-center">
      {isListening && (
        <div className="absolute inset-0 bg-accent-red/30 rounded-full animate-pulse-fast scale-[1.3]"></div>
      )}
      <button
        onClick={toggleListen}
        className={`relative z-10 p-3 rounded-full transition-colors shadow-lg ${
          isListening 
            ? 'bg-accent-red text-white' 
            : 'bg-primary-container text-primary-DEFAULT hover:bg-primary-container/80'
        }`}
        title={isListening ? "Stop Listening" : "Start Voice Input"}
      >
        {isListening ? <MicOffIcon className="w-6 h-6 shrink-0" /> : <MicIcon className="w-6 h-6 shrink-0" />}
      </button>
    </div>
  );
}
