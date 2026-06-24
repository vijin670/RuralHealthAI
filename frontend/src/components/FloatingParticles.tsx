
export function FloatingParticles() {
  return (
    <div className="fixed inset-0 pointer-events-none overflow-hidden z-0">
      {/* Pulse Glow/Gradients */}
      <div className="absolute top-[10%] left-[20%] w-[30rem] h-[30rem] rounded-full bg-primary-DEFAULT/5 blur-[120px] animate-pulse-glow" />
      <div className="absolute bottom-[20%] right-[10%] w-[35rem] h-[35rem] rounded-full bg-secondary-DEFAULT/5 blur-[150px] animate-pulse-glow" style={{ animationDelay: '-4s' }} />

      {/* Floating Medical Symbols */}
      {/* Medical Cross 1 */}
      <svg
        className="absolute top-[15%] left-[8%] w-10 h-10 text-primary-light/10 fill-current animate-float-slow"
        viewBox="0 0 24 24"
      >
        <path d="M19 10.5h-5.5V5c0-.83-.67-1.5-1.5-1.5s-1.5.67-1.5 1.5v5.5H5c-.83 0-1.5.67-1.5 1.5s.67 1.5 1.5 1.5h5.5V19c0 .83.67 1.5 1.5 1.5s1.5-.67 1.5-1.5v-5.5H19c.83 0 1.5-.67 1.5-1.5s-.67-1.5-1.5-1.5z" />
      </svg>

      {/* Heart 1 */}
      <svg
        className="absolute top-[40%] right-[8%] w-8 h-8 text-secondary-light/10 fill-current animate-float-slower"
        viewBox="0 0 24 24"
      >
        <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" />
      </svg>

      {/* Medical Cross 2 */}
      <svg
        className="absolute bottom-[25%] left-[12%] w-8 h-8 text-primary-light/8 fill-current animate-float-slower"
        viewBox="0 0 24 24"
        style={{ animationDelay: '-3s' }}
      >
        <path d="M19 10.5h-5.5V5c0-.83-.67-1.5-1.5-1.5s-1.5.67-1.5 1.5v5.5H5c-.83 0-1.5.67-1.5 1.5s.67 1.5 1.5 1.5h5.5V19c0 .83.67 1.5 1.5 1.5s1.5-.67 1.5-1.5v-5.5H19c.83 0 1.5-.67 1.5-1.5s-.67-1.5-1.5-1.5z" />
      </svg>

      {/* Pill 1 */}
      <svg
        className="absolute top-[25%] right-[25%] w-10 h-10 text-primary-light/8 fill-current rotate-45 animate-float-slow"
        viewBox="0 0 24 24"
        style={{ animationDelay: '-6s' }}
      >
        <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-2 10H7v-2h10v2z" />
      </svg>

      {/* Pill 2 (capsule) */}
      <svg
        className="absolute bottom-[15%] right-[20%] w-9 h-9 text-secondary-light/8 fill-current -rotate-12 animate-float-slower"
        viewBox="0 0 24 24"
        style={{ animationDelay: '-8s' }}
      >
        <path d="M6 3h12c1.66 0 3 1.34 3 3v12c0 1.66-1.34 3-3 3H6c-1.66 0-3-1.34-3-3V6c0-1.66 1.34-3 3-3zm0 2c-.55 0-1 .45-1 1v5h14V6c0-.55-.45-1-1-1H6zm14 8H4v5c0 .55.45 1 1 1h12c.55 0 1-.45 1-1v-5z" />
      </svg>

      {/* DNA Helix fragment */}
      <svg
        className="absolute top-[55%] left-[20%] w-12 h-12 text-primary-DEFAULT/5 fill-none stroke-current stroke-2 animate-drift-slow"
        viewBox="0 0 24 24"
      >
        <path d="M4.5 10.5C6 14.5 9 17.5 12 17.5s6-3 7.5-7m-15 3C6 9.5 9 6.5 12 6.5s6 3 7.5 7" />
        <circle cx="4.5" cy="10.5" r="1.5" fill="currentColor" />
        <circle cx="19.5" cy="10.5" r="1.5" fill="currentColor" />
        <circle cx="12" cy="17.5" r="1.5" fill="currentColor" />
        <circle cx="12" cy="6.5" r="1.5" fill="currentColor" />
      </svg>

      {/* Pulse lines */}
      <svg
        className="absolute bottom-[45%] left-[5%] w-16 h-12 text-secondary-DEFAULT/5 fill-none stroke-current stroke-2 animate-pulse-glow"
        viewBox="0 0 24 24"
        style={{ animationDelay: '-2s' }}
      >
        <path d="M2 12h4l2-6 3 12 2-9 2 5 2-2h5" />
      </svg>
    </div>
  );
}
