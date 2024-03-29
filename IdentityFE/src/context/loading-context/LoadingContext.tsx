import React, {createContext, useContext, useState} from 'react';

type LoadingContextType = {
    loading: boolean;
    setLoading: (loading: boolean) => void;
};

const LoadingContext = createContext<LoadingContextType>({
    loading: false,
    setLoading: () => {
    },
});

export function LoadingProvider({children}: { children: React.ReactNode }) {
    const [loading, setLoading] = useState(false);
    const value = {loading, setLoading};
    return (
        <LoadingContext.Provider value={value}>{children}</LoadingContext.Provider>
    );
}

export function useLoading() {
    const context = useContext(LoadingContext);
    if (!context) {
        throw new Error("useLoading must be used within LoadingProvider");
    }
    return context;
}